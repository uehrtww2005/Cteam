<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../header.html" %>
<%@ include file="../store_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/side.css">

<style>
.calendar-table { border-collapse: collapse; margin-bottom: 30px; width: 100%; }
.calendar-table th, .calendar-table td {
    border: 1px solid #aaa;
    padding: 8px;
    text-align: center;
    width: 14%;
    height: 60px;
    cursor: pointer;
    white-space: pre-line;a
}
.calendar-table th { background-color: #f0f0f0; }

.modal {
    display:none;
    position:fixed;
    top:20%;
    left:30%;
    width:40%;
    background:#222;
    border:1px solid #000;
    padding:20px;
    z-index:1000;
}

/* 過去日は見た目上無効化 */
td.past {
    background-color: #eee;
    color: #aaa;
    pointer-events: none;
    cursor: default;
}
</style>
</head>

<body>
<h1>店舗詳細編集</h1>

<form id="storeForm" action="StoreDetailUpdate.action" method="post">
    <input type="hidden" name="store_id" value="${detail.storeId}">

    <h3>店舗紹介文</h3>
    <textarea name="storeIntroduct" rows="4" cols="50">${detail.storeIntroduct}</textarea>

    <h3>タグ選択</h3>
    <select name="tag">
        <option value="">選択してください</option>
        <c:forEach var="t" items="${allTags}">
            <option value="${t.tagName}" <c:if test="${detail.tag == t.tagName}">selected</c:if>>${t.tagName}</option>
        </c:forEach>
    </select>

    <hr>
    <h3>席情報</h3>
    <div id="seatList">
        <c:forEach var="s" items="${detail.seats}">
            <div class="seatItem">
                <input type="text" name="seatName" value="${s.seatName}" />
                <input type="text" name="seatType" value="${s.seatType}" />
                <input type="number" name="minPeople" value="${s.minPeople}" />
            </div>
        </c:forEach>
    </div>
    <button type="button" onclick="addSeat()">席を追加</button>

    <hr>
    <h3>営業カレンダー（今月から1年）</h3>
    <div id="calendar-wrapper">
        <button type="button" id="prev-month">&lt;</button>
        <button type="button" id="next-month">&gt;</button>

        <!-- サーバのカレンダーデータを JS に変換 -->
        <script>
            let calendarData = {};
            <c:if test="${not empty detail.calendars}">
                <c:forEach var="c" items="${detail.calendars}">
                		calendarData["${c.dateStr}"] = {
                        isClosed: ${!c.open},
                        openTime: "${c.openTimeStr}",
                        closeTime: "${c.closeTimeStr}"
                    };
                </c:forEach>
            </c:if>
        </script>

        <c:forEach var="y" items="${years}" varStatus="loop">
            <c:set var="m" value="${months[loop.index]}" />
            <c:set var="start" value="${startDays[loop.index]}" />
            <c:set var="end" value="${lastDays[loop.index]}" />

            <div class="calendar" data-month="${y}-${m}" style="display:none;">
                <h2>${y}年 ${m}月</h2>
                <table class="calendar-table">
                    <tr>
                        <th style="color:red;">日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th style="color:blue;">土</th>
                    </tr>
                    <tr>
                    <c:set var="startIndex" value="${start - 1}" />
                    <c:forEach var="d" begin="1" end="${end}">
                        <c:set var="w" value="${(startIndex + (d - 1)) % 7}" />
                        <c:if test="${d == 1}">
                            <c:forEach begin="1" end="${w}"><td></td></c:forEach>
                        </c:if>
                        <c:set var="mm" value="${m < 10 ? '0' + m : m}" />
                        <c:set var="dd" value="${d < 10 ? '0' + d : d}" />
                        <c:set var="key" value="${y}-${mm}-${dd}" />
                        <td data-date="${key}">
                            ${d}<br>未入力
                        </td>
                        <c:if test="${w == 6}"></tr><tr></c:if>
                    </c:forEach>
                    </tr>
                </table>
            </div>
        </c:forEach>

         <h3>クーポン追加</h3>

		    <label>ランク：</label>
		    <select name="new_coupon_rank">
		        <option value="">選択してください</option>
		        <option value="ゴールド">ゴールド</option>
		        <option value="シルバー">シルバー</option>
		        <option value="ブロンズ">ブロンズ</option>
		    </select><br>

		    <label>説明：</label>
		    <textarea name="new_coupon_introduct"></textarea><br>

    		<button type="submit">追加する</button>
    <div id="calendarHiddenInputs"></div>
    <button type="submit">保存</button>
</form>

<!-- モーダル -->
<div id="calendarModal" class="modal">
    <h3 id="modalDate"></h3>
    <label><input type="checkbox" id="isClosed" onchange="onClosedChange();"> 定休日</label><br><br>
    <label>開店時間: <input type="time" id="openTime" onchange="updateCalendarData();"></label><br>
    <label>閉店時間: <input type="time" id="closeTime" onchange="updateCalendarData();"></label><br><br>
    <button type="button" onclick="closeCalendarModal()">閉じる</button>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const today = new Date();
    today.setHours(0,0,0,0);

    const calendarNodes = Array.from(document.querySelectorAll(".calendar"));
    const storeForm = document.getElementById("storeForm");
    const hiddenBox = document.getElementById("calendarHiddenInputs");

    let currentMonth = 0;
    function showMonth(index){
        calendarNodes.forEach((cal,i)=>cal.style.display = i===index?"block":"none");
    }

    const prevBtn = document.getElementById("prev-month");
    const nextBtn = document.getElementById("next-month");
    if(prevBtn) prevBtn.addEventListener("click", ()=>{ if(currentMonth>0) currentMonth--; showMonth(currentMonth); });
    if(nextBtn) nextBtn.addEventListener("click", ()=>{ if(currentMonth<calendarNodes.length-1) currentMonth++; showMonth(currentMonth); });
    showMonth(currentMonth);

    // カレンダー初期化
    document.querySelectorAll("td[data-date]").forEach(td=>{
        const dateStr = td.getAttribute("data-date");
        if(!dateStr) return;

        // まずセルにDBデータを反映
        updateCalendarCell(dateStr);

        // 過去日判定
        const cellDate = new Date(dateStr);
        cellDate.setHours(0,0,0,0);
        if(cellDate < today){
            td.classList.add("past");  // 過去日はグレーでクリック不可
        } else {
            td.addEventListener("click", ()=>{ openCalendarModal(dateStr); }); // 今日以降はクリック可
        }
    });

    // モーダル制御
    let currentEditingDate = null;
    window.openCalendarModal = function(dateStr){
        currentEditingDate = dateStr;
        const data = calendarData[dateStr] || {isClosed:false, openTime:"", closeTime:""};
        document.getElementById("modalDate").innerText = dateStr;
        document.getElementById("isClosed").checked = data.isClosed;
        document.getElementById("openTime").value = data.openTime;
        document.getElementById("closeTime").value = data.closeTime;
        document.getElementById("openTime").disabled = data.isClosed;
        document.getElementById("closeTime").disabled = data.isClosed;
        document.getElementById("calendarModal").style.display = "block";
    };
    window.closeCalendarModal = function(){ document.getElementById("calendarModal").style.display="none"; };
    window.onClosedChange = function(){
        const c = document.getElementById("isClosed").checked;
        document.getElementById("openTime").disabled = c;
        document.getElementById("closeTime").disabled = c;
        updateCalendarData();
    };

    // データ更新とセル更新
    window.updateCalendarData = function(){
        if(!currentEditingDate) return;
        calendarData[currentEditingDate] = {
            isClosed: document.getElementById("isClosed").checked,
            openTime: document.getElementById("openTime").value,
            closeTime: document.getElementById("closeTime").value
        };
        updateCalendarCell(currentEditingDate);
    };

    function updateCalendarCell(dateStr){
        const td = document.querySelector("td[data-date='"+dateStr+"']");
        if(!td) return;
        const d = parseInt(dateStr.split("-")[2],10);
        const data = calendarData[dateStr] || {isClosed:false, openTime:"", closeTime:""};

        if(data.isClosed) td.innerHTML = d+"<br><span style='color:red;'>×</span>";
        else if(data.openTime && data.closeTime) td.innerHTML = d+"<br>"+data.openTime+"～"+data.closeTime;
        else td.innerHTML = d+"<br>未入力";

        // 過去日判定：更新後もグレー維持
        const cellDate = new Date(dateStr);
        cellDate.setHours(0,0,0,0);
        if(cellDate < today) td.classList.add("past");
    }

    // submit前 hidden生成
    if(storeForm){
        storeForm.onsubmit = function(){
            if(!hiddenBox) return true;
            hiddenBox.innerHTML="";
            for(let date in calendarData){
                if(!calendarData.hasOwnProperty(date)) continue;
                const d = calendarData[date];
                hiddenBox.innerHTML += '<input type="hidden" name="date[]" value="'+date+'">';
                hiddenBox.innerHTML += '<input type="hidden" name="isClosed[]" value="'+d.isClosed+'">';
                hiddenBox.innerHTML += '<input type="hidden" name="openTime[]" value="'+(d.isClosed?"":d.openTime)+'">';
                hiddenBox.innerHTML += '<input type="hidden" name="closeTime[]" value="'+(d.isClosed?"":d.closeTime)+'">';
            }
            return true;
        };
    }
});
</script>


<%@include file="../footer.html" %>
