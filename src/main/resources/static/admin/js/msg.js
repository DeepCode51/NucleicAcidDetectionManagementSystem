

var msg = {};

function errorMsg(content)
{
    layer.msg(content.toString(), {icon:5});
}

function successMsg(content)
{
    layer.msg(content,{icon:1});
}

msg.isEmpty = function(obj)
{
    if(obj == null || obj.length == 0)
    {
        return true;
    }

    return false;
}

//邮箱验证
msg.isEmail = function(email)
{
    var checkEmail = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    if(!checkEmail.test(email)) {
        return false;
    }
    return true;
}

//手机号验证
msg.isPhone = function(phone)
{
    var checkPhone = /^1[3|4|5|7|8]\d{9}$/;
    if(!checkPhone.test(phone)) {
        return false;
    }

    return true;
}

//身份证号验证
msg.isCard = function( card )
{
    var checkCard = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if(!checkCard.test(card)) {
        return false;
    }
    return true;
}

//年龄验证
msg.isAge = function( age )
{
    var checkAge =  /^(?:[1-9][0-9]?|1[01][0-9]|120)$/;
    if(!checkAge.test(age)) {
        return false;
    }

    return true;
}

//确认框
msg.confirm = function(title, btnClick1,  btnClick2)
{
    layer.confirm(title,
        {
            btn: ['确定','取消'] //按钮
        },
        function(index, layero){
            if(btnClick1 == null)
                layer.close(index);
            else
                btnClick1(index, layero);
        },
        function(index, layero){
            if(btnClick2 == null)
                layer.close(index);
            else
                btnClick2(index, layero);
        }
    );
}

//判断银行卡
msg.isBankCard = function(backCard) {
    var bank = /^\d+$/
    if (!bank.test(backCard) || backCard == "") {//bankCode为银行卡号
        errorMsg("请输入正确的银行卡号");
        return false;
    } else {
        return true;
    }
}
/*msg.isBankCard = function(backCard,theBank, successHandler, errorHandler){
    var bank = /^\d+$/
    if (!bank.test(backCard) || backCard == "") {//bankCode为银行卡号
        errorMsg("请输入正确的银行卡号");
        return false;
    }else{
        $.ajax({
            dataType:'json',
            async: false,
            type:'get',
            url:'https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true',
            data:{cardNo:backCard},
            success:function(data){
                if(data.validated == false){
                    errorMsg("请输入正确的银行卡号");
                    return false;
                }else if(data.validated == true){
                    if(data.cardType == 'DC'){
                        var cardBank = data.bank;
                        if(cardBank != theBank){
                            errorMsg("银行与卡号不匹配！");
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        errorMsg("请输入储蓄卡卡号！");
                        return false;
                    }
                }
            }
        })
    }

}*/

