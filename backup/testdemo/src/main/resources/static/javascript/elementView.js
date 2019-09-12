//login.html
function submitOnMouseUpL() {
    var loginSubmit = document.getElementById("loginSubmit");
    loginSubmit.style.backgroundColor = "#EE7700";
}

function submitOnMouseLeaveL() {
    var loginSubmit = document.getElementById("loginSubmit");
    loginSubmit.style.backgroundColor = "#FFA500";
}

//register.html
function submitOnMouseUpR() {
    var registerSubmit = document.getElementById("registerSubmit");
    registerSubmit.style.backgroundColor = "#EE7700";
}

function submitOnMouseLeaveR() {
    var registerSubmit = document.getElementById("registerSubmit");
    registerSubmit.style.backgroundColor = "#FFA500";
}

//infoHallOnTime.html

//infoHallHistory.html

//objectAdd.html

//indexUser.html
var curIndex = 0;
//时间间隔(单位毫秒)，每3秒钟显示一张
var timeInterval = 3000;
var arr = new Array();
arr[0] = '[[${mainPicList.get(0).getPicUrl()}]]';
arr[1] = '[[${mainPicList.get(1).getPicUrl()}]]';
arr[2] = '[[${mainPicList.get(2).getPicUrl()}]]';
// arr[0]="http://img17.3lian.com/d/file/201702/14/3d1d78481dbe5db4802f4b1eb548f365.jpg";
// arr[1]="http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1608/16/c5/25611861_1471306140723_mthumb.jpg";
// arr[2]="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551030493075&di=5f5ebd3ddd0ec01e28291daa2f60068f&imgtype=0&src=http%3A%2F%2Fimg18.3lian.com%2Fd%2Ffile%2F201710%2F24%2F4cc802c11c2fa1d64c6a89afc3cb9819.jpg";
// arr[0]="images/1.jpg";
// arr[1]="images/2.jpg";
// arr[2]="images/3.jpg";
setInterval(changeImg, timeInterval);

function changeImg() {
    var obj = document.getElementById("mainPic");
    if (curIndex === arr.length - 1) {
        curIndex = 0;
    }
    else {
        curIndex += 1;
    }
    obj.src = arr[curIndex];
}

function fontBold1() {
    // var more = document.getElementsByClassName("moreClass");
    var more = document.getElementById("moreClass1");
    more.style.fontWeight = "bold";
}

function fontNormal1() {
    // var more = document.getElementsByClassName("moreClass");
    var more = document.getElementById("moreClass1");
    more.style.fontWeight = "normal";
}

function fontBold2() {
    // var more = document.getElementsByClassName("moreClass");
    var more = document.getElementById("moreClass2");
    more.style.fontWeight = "bold";
}

function fontNormal2() {
    // var more = document.getElementsByClassName("moreClass");
    var more = document.getElementById("moreClass2");
    more.style.fontWeight = "normal";
}

function fontBold3() {
    // var more = document.getElementsByClassName("moreClass");
    var more = document.getElementById("moreClass3");
    more.style.fontWeight = "bold";
}

function fontNormal3() {
    // var more = document.getElementsByClassName("moreClass");
    var more = document.getElementById("moreClass3");
    more.style.fontWeight = "normal";
}
