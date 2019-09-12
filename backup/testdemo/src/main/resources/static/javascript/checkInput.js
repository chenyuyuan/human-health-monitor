function checkForm() {
    var form = document.getElementById("loginForm");
    if (form.userId.value == "" || form.userName.value == "" || form.birthday.value == "" ||
        form.password1.value == "" || form.password2.value == "" || form.telephone.value == "") {
        alert("您有信息尚未填写，请补充未填写的信息");
        window.event.returnValue = false;//验证失败不提交action
        // return false;
    }
    else if (form.userId.value.length > 32 || form.userName.value.length > 32 || form.password1.value.length > 32 ||
        form.password2.value.length > 32)
    // || form.telephone.value.length > 32
    {
        alert("账号、昵称、密码、手机号码均不能超过32个字符");
        window.event.returnValue = false;//验证失败不提交action
    }
    else if (!/^\w+$/.test(form.userId.value)) {
        alert("账号只能由字母、数字和下划线组成");
        window.event.returnValue = false;//验证失败不提交action
    }
    else if (!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(form.userName.value)) {
        alert("用户昵称只能由汉字、字母、数字和下划线组成");
        window.event.returnValue = false;//验证失败不提交action
    }
    else if (form.password1.value != form.password2.value) {
        alert("两次输入密码不一致！");
        window.event.returnValue = false;//验证失败不提交action
    }
    else if (form.password1.value.length < 8) {
        alert("请输入至少8个字符的密码！");
        window.event.returnValue = false;//验证失败不提交action
    }
    else if (!/^\w+$/.test(form.password1.value)) {
        alert("密码只能由字母、数字和下划线组成");
        window.event.returnValue = false;//验证失败不提交action
    }
    else if (!/^[1][3,4,5,7,8][0-9]{9}$/.test(form.telephone.value)) {
        alert("手机号码格式错误，请填写正确的手机号");
        window.event.returnValue = false;//验证失败不提交action
    }
}

