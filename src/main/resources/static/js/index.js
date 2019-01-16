/**
 * Created by CC on 2018/2/24.
 */
$(document).ready(function(){
    $('#ith').combobox({
        panelHeight:90,
        onChange:function(newVal, oldVal){
            var oldHref = $('#uiTheme').attr('href');
            var newHref = oldHref.substring(0,oldHref.indexOf('themes')) + 'themes/' + newVal + '/easyui.css';
            //console.log(newHref);
            $('#uiTheme').attr('href', newHref);
            //设置cookie值，并设置7天有效时间
            $.cookie('themeName', newVal, {
                path: '/',
                expires : 7
            });
            $('#theamwin').window('close');
            window.location.reload();
        }
    });

    //if(<%=SYS_SJZX!=null%>){
        $("#right_btn").show();
    //}
    foodHover(20,20);
    setTimeout("picSwitch()",2000);
});
function logout(){
    var param={'user.username':'<%=user.getUsername()%>'};
    $.messager.confirm('确认','确认退出系统吗？',function(r){
        if (r){
            $.ajax({
                type: "POST",
                url: '/logout',
                data:param,
                async: false,
                error: function(request) {
                    alert("失去连接，操作异常");
                },
                success: function(data) {
                }
            });
        }
    });
}
function opentheam(){
    $('#theamwin').window({title:'换肤',iconCls:'icon-add'});
    $('#theamwin').window('open');
}
function opensysmng(){
    $('#theamwin').window({title:'换肤',iconCls:'icon-add'});
    $('#theamwin').window('open');
}
function refresh(){
    window.location.reload();//刷新当前页面.
}
function openOpt(title,url){
    $('#optwin').window({title:title,iconCls:'icon-add'
        /*,onBeforeClose: function () {
         if (confirm('窗口正在关闭，请确认您当前的操作已保存。\n 是否继续关闭窗口？')) {
         $("#updatewin").window('close', true);
         } else
         return false;
         }*/
    });
/*<%for(int i=0;i<onlineCount;i++){
        User user2=userlist.get(i);
        String userpics="js/jquery-easyui-1.4.5/themes/myicons/user.png";
        if(user2.getUserpic()!=null){
            userpics=user2.getUserpic();
        }
        String username=user2.getUsername();
        if(username.indexOf("'")>-1){
            username=username.replace("'", "");
        }
        html+="<div style=\"height:25px;width:80%;padding-top:5px;padding-left:45px;\"><img style=\"height:25px;width:25px;\" src=\""+userpics+"\">"+username+"<br></div>";
    %>
    <%}%>*/
    $('#optdiv').html('<%=html%>');
    $('#optwin').window('open');
}

function foodHover(x,y){
    var $food_main=$("#food_main");
    $food_main.find(".main_div").each(function(){
        var l=$(this).position().left;
        var t=$(this).position().top;
        var w=$(this).width();
        var h=$(this).height();
        $(this).mouseover(function(){
            $(this).stop(true,true).css("z-index",10).animate({"width":w+x,"height":h+y,"left": l-(x/2),"top":t-(y/2)},300)
        }).mouseout(function(){
            $(this).stop(true,true).css("z-index",0).animate({"width":w,"height":h,"left": l,"top":t},300);
        });
    });
}
function picSwitch() {
    $left_btn=$("#left_btn");
    $right_btn=$("#right_btn");
    var len=$(".food_content").length;
    var modulesize=$("#modulesize").val();
    if(modulesize==0){
        $(".food_content").css("width","100%");
        len=1;
    }else if(modulesize>0&&modulesize<=12){
        $(".food_content").css("width","50%");
        len=2;
    }else if(modulesize>12){
        $(".food_content").css("width","33%");
        len=3;
    }
    var i=0;
    var w=$("body").width();
    $(".food_middle_main").width(len*100+"%");
    $left_btn.on({
        mouseover:function(){
            $(this).addClass("food_btn_left_hover")
        },
        mouseout:function () {
            $(this).removeClass("food_btn_left_hover")
        },
        click:function(){
            $("#bottom").show();
            if(i==0){
                $left_btn.hide();
            }else{
                i--;
                $right_btn.show();
                if(! $(".food_middle_main").is(":animated")){
                    $(".food_middle_main").animate({"left":-w*i},500);
                }
                if(i==0){
                    $left_btn.hide();
                }

            }
        }
    });
    //if(<%=SYS_SJZX!=null%>){
        $right_btn.on({
            mouseover:function(){
                $(this).addClass("food_btn_right_hover")
            },
            mouseout:function () {
                $(this).removeClass("food_btn_right_hover")
            },
            click:function(){
                if(i<len-1){
                    i++;
                    $left_btn.show();
                    if(! $(".food_middle_main").is(":animated")){
                        $(".food_middle_main").animate({"left":-w*i},500);
                    }
                    if(i==len-1){
                        $right_btn.hide();
                    }
                }else {
                    $right_btn.hide();
                }
            }
        });
    //}
    //if(<%=SYS_SJZX!=null%>){
        $(".main_div_4").css("cursor","pointer");
        $(".main_div_4").on("click",function(){
            i=1;
            $left_btn.show();
            $(this).parents(".food_middle_main").animate({"left":-w*i},500);
            if(i==len-1){
                $right_btn.hide();
            }
            return false;
        });
    //}

}

function characteristicMode(id){
    /*console.log(l)*/
    $id_name=$("."+id);
    $id_name.on("click",function(e){
        $(this).parents(".food_middle_main").animate({"left":-w*i},500);

        return false;
    })
}
function setModuleSize(modulesize){
    $("#modulesize").val(modulesize);
}
function openSet(){
    window.open('<%=basePath%>page/main/index.jsp?moduleid=<%=SYS_XTGL%>','_self');
}
function gotoPage(url){
    window.open('<%=basePath%>'+url,'_self');
}