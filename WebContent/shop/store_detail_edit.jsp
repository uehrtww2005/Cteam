<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../header.html" %>
<%@ include file="../store_side.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/store_side.css">

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
.calendar-table th { background-color: #777777; }

/* ===== モーダル基本デザイン ===== */
/* ======== モーダル（スタイリッシュ版） ======== */
.modal {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 420px;
    background: #1b1b1b;
    border-radius: 12px;
    padding: 25px 30px;
    box-shadow: 0 0 20px rgba(0,0,0,0.7);
    z-index: 2000;
    color: #fff;
    animation: fadeIn 0.25s ease-out;
    border: 1px solid #333;
}

/* ふわっと出すアニメーション */
@keyframes fadeIn {
    from { opacity: 0; transform: translate(-50%, -45%); }
    to   { opacity: 1; transform: translate(-50%, -50%); }
}

/* モーダル内のタイトル */
#modalDate {
    font-size: 1.4rem;
    margin-bottom: 15px;
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #444;
    padding-bottom: 10px;
}

/* input, time選択をスタイリッシュに */
.modal input[type="time"],
.modal textarea,
.modal select {
    width: 100%;
    padding: 10px;
    margin-top: 5px;
    background: #2a2a2a;
    border: 1px solid #444;
    border-radius: 6px;
    color: #fff;
    font-size: 1rem;
}

/* チェックボックス行を揃える */
.modal label {
    display: block;
    margin-top: 12px;
    font-size: 1rem;
}

/* 送信ボタン */
form button {
    margin-top: 25px;
    background-color: #FFD700 !important;
    color: #ffffff !important;
    font-weight: bold;
    font-size: 16px;
    padding: 12px 0;
    width: 100%;
    border: none !important;
    border-radius: 8px !important;
    cursor: pointer !important;
    background: linear-gradient(145deg, #222, #111) !important;
    border:1px solid #666 !important;
    box-shadow: 0 4px 10px rgba(0,0,0,0.5) !important;
    transition: 0.3s !important;
}

form button:hover {
    background: linear-gradient(145deg, #333, #000) !important;
    color: #FFD700 !important;
    border-color: #FFD700 !important;
    transform: translateY(-2px) !important;
    box-shadow: 0 0 15px rgba(255,215,0,0.3) !important;
}

/* 過去日は見た目上無効化 */
td.past {
    background-color: #eee;
    color: #aaa;
    pointer-events: none;
    cursor: default;
}
.month-nav {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 16px;
    margin: 20px 0;
}

.month-nav button {
    background: #333;
    color: #fff;
    border: none;
    padding: 6px 14px;
    font-size: 1.4rem;
    border-radius: 6px;
    cursor: pointer;
	position: relative;
    top: -10px;
}

.month-nav button:hover {
    background: #555;
}

#current-month-label {
    font-size: 1.4rem;
    font-weight: bold;
    min-width: 180px;
    text-align: center;
}

.store-main h3 {
    font-size: 18px;        /* ← 元に近いサイズ */
    font-weight: bold;
    letter-spacing: normal;
    border-bottom: none;    /* ← 黄色い下線を消す */
    padding-bottom: 0;
}

.store-main h1 {
    color: #fff;
    margin-bottom: 20px;
    padding-bottom: 8px;
    border-bottom: 2px solid #FFD700; /* 下線 */
    display: inline-block;
}

/* タグ選択用 select のサイズ */
.tag-select {
  width: 320px;
  height: 48px;
  font-size: 18px;
  padding: 6px 12px;
  box-sizing: border-box;

  /* 背景になじませる */
  background: rgba(0, 0, 0, 0.45);
  color: #ffffff;

  border: 1px solid #666;
  border-radius: 8px;

  outline: none;
  cursor: pointer;

  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
}

/* フォーカス時（クリック時） */
.tag-select:focus {
  border-color: #FFD700;
  box-shadow: 0 0 0 2px rgba(255, 215, 0, 0.25);
}

/* option の背景（重要） */
.tag-select option {
  background: #111;
  color: #fff;
}

textarea[name="storeIntroduct"] {
  width: 100%;
  max-width: 600px;
  min-height: 120px;

  padding: 12px 14px;
  box-sizing: border-box;

  background: rgba(0, 0, 0, 0.45);
  color: #ffffff;

  border: 1px solid #666;
  border-radius: 8px;

  font-size: 16px;
  line-height: 1.6;

  resize: vertical;
  outline: none;
}

/* フォーカス時 */
textarea[name="storeIntroduct"]:focus {
  border-color: #FFD700;
  box-shadow: 0 0 0 2px rgba(255, 215, 0, 0.25);
}

/* プレースホルダー色（もし使うなら） */
textarea[name="storeIntroduct"]::placeholder {
  color: #aaa;
}



</style>
</head>

<body>
<div class="store-main">
<h1>店舗詳細編集</h1>

<form id="storeForm" action="StoreDetailUpdate.action" method="post">
<input type="hidden" name="store_id" value="${detail.storeId}">

    <h3>店舗紹介文</h3><br>
<textarea name="storeIntroduct" rows="4" cols="50">${detail.storeIntroduct}</textarea>
<br>
<h3>タグ選択</h3><br>
<select name="tag" class="tag-select">
  <option value="">選択してください</option>
  <c:forEach var="t" items="${allTags}">
    <option value="${t.tagName}"
      <c:if test="${detail.tag == t.tagName}">selected</c:if>>
      ${t.tagName}
    </option>
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
<div class="month-nav">
    <button type="button" id="prev-month">＜</button>
    <span id="current-month-label"></span>
    <button type="button" id="next-month">＞</button>
</div>

<c:forEach var="y" items="${years}" varStatus="loop">
<c:set var="m" value="${months[loop.index]}" />
<c:set var="start" value="${startDays[loop.index]}" />
<c:set var="end" value="${lastDays[loop.index]}" />

<div class="calendar" data-month="${y}-${m}" style="display:none;">
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

<div id="calendarHiddenInputs"></div>
<button type="submit">保存</button>
</form>
</div>
<!-- モーダル -->
<div id="calendarModal" class="modal">
    <h3 id="modalDate"></h3>

    <label>
        <input type="checkbox" id="isClosed" onchange="onClosedChange();">
        定休日
    </label>
    <br><br>

    <label>開店時間:
        <select id="openTime" onchange="updateCalendarData();">
            <option value="">--</option>
        </select>
    </label>
    <br>

    <label>閉店時間:
        <select id="closeTime" onchange="updateCalendarData();">
            <option value="">--</option>
        </select>
    </label>
    <br><br>

    <button type="button" onclick="closeCalendarModal()">閉じる</button>
</div>


<script>
function pad(n) { return n.toString().padStart(2, '0'); }

function normalizeDate(dateStr){
    const p = dateStr.split("-");
    return p[0] + "-" + pad(p[1]) + "-" + pad(p[2]);
}

function addSeat() {

    const div = document.createElement("div");

    div.className = "seatItem";

    div.innerHTML = `
<input type="text" name="seatName" placeholder="席名">
<input type="text" name="seatType" placeholder="タイプ">
<input type="number" name="minPeople" placeholder="最高人数">`;

    document.getElementById("seatList").appendChild(div);

}

// 初期カレンダーデータを JS に変換
let calendarData = {};
<c:if test="${not empty detail.calendars}">
<c:forEach var="c" items="${detail.calendars}">
    calendarData["${c.dateStr}"] = {
        isClosed: ${!c.open},       // ここ重要。ELでisOpen()の値を参照
        openTime: "${c.openTimeStr}",
        closeTime: "${c.closeTimeStr}"
    };
</c:forEach>
</c:if>

document.addEventListener("DOMContentLoaded", function() {

    const today = new Date();
    today.setHours(0,0,0,0);

    const calendarNodes = Array.from(document.querySelectorAll(".calendar"));
    const storeForm = document.getElementById("storeForm");
    const hiddenBox = document.getElementById("calendarHiddenInputs");

    let currentMonth = 0;

    function showMonth(index){
        for (let i = 0; i < calendarNodes.length; i++) {
            calendarNodes[i].style.display = (i === index) ? "block" : "none";
        }
        const label = document.getElementById("current-month-label");
        if (!label) return;
        const cal = calendarNodes[index];
        if (!cal) return;
        const ym = cal.getAttribute("data-month");
        if (!ym) return;
        const arr = ym.split("-");
        label.textContent = arr[0] + "年 " + parseInt(arr[1], 10) + "月";
    }

    document.getElementById("prev-month").addEventListener("click", ()=>{ if(currentMonth>0) currentMonth--; showMonth(currentMonth); });
    document.getElementById("next-month").addEventListener("click", ()=>{ if(currentMonth<calendarNodes.length-1) currentMonth++; showMonth(currentMonth); });
    showMonth(currentMonth);

    // カレンダー各セル初期化
    document.querySelectorAll("td[data-date]").forEach(td=>{
        let dateStr = normalizeDate(td.getAttribute("data-date"));
        td.setAttribute("data-date", dateStr); // 常にゼロ埋め
        updateCalendarCell(dateStr);

        const cellDate = new Date(dateStr);
        cellDate.setHours(0,0,0,0);
        if(cellDate < today) td.classList.add("past");
        else td.addEventListener("click", ()=>{ openCalendarModal(dateStr); });
    });

    // モーダル制御
    let currentEditingDate = null;

    window.openCalendarModal = function(dateStr){
        const normalizedDate = normalizeDate(dateStr);
        currentEditingDate = normalizedDate;

        const data = calendarData[normalizedDate] || {isClosed:false, openTime:"", closeTime:""};
        document.getElementById("modalDate").innerText = normalizedDate;
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

    window.updateCalendarData = function(){
        if(!currentEditingDate) return;
        const normalizedDate = normalizeDate(currentEditingDate);
        currentEditingDate = normalizedDate;

        calendarData[normalizedDate] = {
            isClosed: document.getElementById("isClosed").checked,
            openTime: document.getElementById("openTime").value,
            closeTime: document.getElementById("closeTime").value
        };

        updateCalendarCell(normalizedDate);
    };

    function updateCalendarCell(dateStr){
        const td = document.querySelector("td[data-date='"+dateStr+"']");
        if(!td) return;
        const d = parseInt(dateStr.split("-")[2],10);
        const data = calendarData[dateStr] || {isClosed:false, openTime:"", closeTime:""};

        if(data.isClosed) td.innerHTML = d + "<br><span style='color:red;'>×</span>";
        else if(data.openTime && data.closeTime) td.innerHTML = d + "<br>" + data.openTime + "～" + data.closeTime;
        else td.innerHTML = d + "<br>未入力";

        const cellDate = new Date(dateStr);
        cellDate.setHours(0,0,0,0);
        if(cellDate < today) td.classList.add("past");
    }

    // submit前 hidden生成
    if(storeForm){
        storeForm.onsubmit = function(){
            if(!hiddenBox) return true;
            hiddenBox.innerHTML = "";
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

function buildTimeOptions() {
    const openSel = document.getElementById("openTime");
    const closeSel = document.getElementById("closeTime");

    for (let h = 0; h < 24; h++) {
        for (let m of [0, 30]) {
            const time = String(h).padStart(2, "0") + ":" + String(m).padStart(2, "0");

            const o1 = document.createElement("option");
            o1.value = time;
            o1.textContent = time;
            openSel.appendChild(o1);

            const o2 = document.createElement("option");
            o2.value = time;
            o2.textContent = time;
            closeSel.appendChild(o2);
        }
    }
}

function onClosedChange() {
    const closed = document.getElementById("isClosed").checked;
    document.getElementById("openTime").disabled = closed;
    document.getElementById("closeTime").disabled = closed;

    if (closed) {
        document.getElementById("openTime").value = "";
        document.getElementById("closeTime").value = "";
    }
    updateCalendarData();
}

document.addEventListener("DOMContentLoaded", function () {
    buildTimeOptions();
});
</script>


<%@include file="../footer.html" %>

