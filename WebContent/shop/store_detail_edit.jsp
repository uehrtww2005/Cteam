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
    white-space: pre-line;
}
.calendar-table th { background-color: #f0f0f0; }
.modal {
    display:none;
    position:fixed;
    top:20%;
    left:30%;
    width:40%;
    background:#fff;
    border:1px solid #000;
    padding:20px;
    z-index:1000;
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

        <script>
            // DB → JS マップ化
            let calendarData = {};
            <c:forEach var="c" items="${detail.calendars}">
                calendarData["${c.date}"] = {
                    isClosed: ${!c.open},
                    openTime: "${c.openTimeStr}",
                    closeTime: "${c.closeTimeStr}"
                };
            </c:forEach>
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
                        <td data-date="${key}" onclick="openCalendarModal('${key}')">
                            ${d}<br>未入力
                        </td>
                        <c:if test="${w == 6}"></tr><tr></c:if>
                    </c:forEach>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </div>

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
// 座席追加
function addSeat() {
    const div = document.createElement("div");
    div.className = "seatItem";
    div.innerHTML = `
        <input type="text" name="seatName" placeholder="席名" />
        <input type="text" name="seatType" placeholder="タイプ" />
        <input type="number" name="minPeople" placeholder="最低人数" />
    `;
    document.getElementById("seatList").appendChild(div);
}

// モーダル制御
let currentEditingDate = null;
function openCalendarModal(date) {
    currentEditingDate = date;
    document.getElementById("modalDate").innerText = date;
    let data = calendarData[date] || {isClosed:false, openTime:"", closeTime:""};
    document.getElementById("isClosed").checked = data.isClosed;
    document.getElementById("openTime").value = data.openTime;
    document.getElementById("closeTime").value = data.closeTime;
    document.getElementById("openTime").disabled = data.isClosed;
    document.getElementById("closeTime").disabled = data.isClosed;
    document.getElementById("calendarModal").style.display = "block";
}
function closeCalendarModal() { document.getElementById("calendarModal").style.display = "none"; }
function onClosedChange() {
    const c = document.getElementById("isClosed").checked;
    document.getElementById("openTime").disabled = c;
    document.getElementById("closeTime").disabled = c;
    updateCalendarData();
}

// カレンダー更新
function updateCalendarData() {
    if(!currentEditingDate) return;
    calendarData[currentEditingDate] = {
        isClosed: document.getElementById("isClosed").checked,
        openTime: document.getElementById("openTime").value,
        closeTime: document.getElementById("closeTime").value
    };
    updateCalendarCell(currentEditingDate);
}
function updateCalendarCell(date){
    const td = document.querySelector("td[data-date='" + date + "']");
    if(!td) return;
    const d = date.split("-")[2];
    const data = calendarData[date];
    if(!data){ td.innerHTML = parseInt(d) + "<br>未入力"; return; }
    if(data.isClosed){ td.innerHTML = parseInt(d) + "<br><span style='color:red;'>×</span>"; return; }
    if(data.openTime && data.closeTime){ td.innerHTML = parseInt(d) + "<br>" + data.openTime + "～" + data.closeTime; return; }
    td.innerHTML = parseInt(d) + "<br>未入力";
}

// 月切替
let currentMonth = 0;
const calendars = document.querySelectorAll(".calendar");
function showMonth(index){
    calendars.forEach((cal,i)=>{ cal.style.display = i===index ? "block" : "none"; });
}
document.getElementById("prev-month").addEventListener("click", ()=>{ if(currentMonth>0) currentMonth--; showMonth(currentMonth); });
document.getElementById("next-month").addEventListener("click", ()=>{ if(currentMonth<calendars.length-1) currentMonth++; showMonth(currentMonth); });

// Submit hidden生成
document.getElementById("storeForm").onsubmit = function() {
    var box = document.getElementById("calendarHiddenInputs");
    box.innerHTML = "";
    for(var date in calendarData){
        if(!calendarData.hasOwnProperty(date)) continue;
        var d = calendarData[date];
        box.innerHTML += '<input type="hidden" name="date[]" value="'+date+'">';
        box.innerHTML += '<input type="hidden" name="isClosed[]" value="'+d.isClosed+'">';
        box.innerHTML += '<input type="hidden" name="openTime[]" value="'+(d.isClosed?"":d.openTime)+'">';
        box.innerHTML += '<input type="hidden" name="closeTime[]" value="'+(d.isClosed?"":d.closeTime)+'">';
    }
    return true;
};

// 初期描画
document.addEventListener("DOMContentLoaded", ()=>{
    document.querySelectorAll("td[data-date]").forEach(td=>{
        updateCalendarCell(td.getAttribute("data-date"));
    });
    showMonth(currentMonth);
});
</script>

<%@include file="../footer.html" %>
