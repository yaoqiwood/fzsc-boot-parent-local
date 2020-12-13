var basicPrefix = HTTP + BAISC_URL + BAISC_CONTEXT;
var imgSrc = basicPrefix + "/sys/randomImage/";
var loginUrl = basicPrefix + '/sys/login';
var randomKey = Math.random();
var btnObj = {
    key: true
};

$(document).ready(() => {
    // 动画
    loginFrameAnimate();
    getRandomImg();
    $("#randomImage").click(() => {
        getRandomImg();
    });

    // 验证码
    $("#loginButton").click(() => {
        $.ajax({
            url: imgSrc + randomKey
        })
    });

    // 按钮
    clickEvent();
});

function getRandomImg() {
    randomKey = Math.random();
    $.ajax({
        url: imgSrc + randomKey,
        type: 'get',
        success: (data) => {
            $("#randomImage").attr("src", data.result);
        }
    });
}

function loginFrameAnimate() {
    $("#header").animate({top: "50px"})
}

function clickEvent() {
    $("#loginButton").click(() => {
        if (!btnObj.key) {
            return
        }
        // 登陆中
        $("#loginButton").html("登陆中...");
        data = {
            username: $("#username").val(),
            password: $("#password").val(),
            captcha: $("#randomInput").val(),
            checkKey: randomKey
        };
        $.ajax({
            url: loginUrl,
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            dataType: 'json',
            data: JSON.stringify(data),
            success(resp) {
                btnObj.key = true;
                $("#loginButton").html("登陆");
                if (resp.success) {
                    alert("登录成功");
                } else {
                    alert(resp.message)
                }
                console.log(resp);
            }
        });
        randomKey = Math.random();
        $("#randomImage").attr("src", imgSrc + randomKey);
    })
}
