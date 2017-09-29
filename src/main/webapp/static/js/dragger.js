function dragger() {
    var contaner = $(".move-space");
    var items = $(".member-container");
    //console.log(items);
    var disX = 0;
    var disY = 0;
    var minZindex = 1;
    var itemPos = [];
    for (var i = 0; i < items.length; i++) {
        var top = items[i].offsetTop;
        var left = items[i].offsetLeft;
        itemPos[i] = {left: left + "px", top: top + "px"};
        itemPos[i].index = i;
    }
    for (var i = 0; i < items.length; i++) {
        items[i].style.position = "absolute";
        items[i].style.top = itemPos[i].top;
        items[i].style.left = itemPos[i].left;
        items[i].style.margin = 0;
        setDrag(items[i]);
    }

    //拖拽事件绑定
    function setDrag(obj) {
        obj.onmouseover = function () {
            //obj.style.cursor = "move";
        };
        obj.onmousedown = function (event) {
            obj.style.zIndex = minZindex++;
            $(items).removeClass("move");
            $(obj).addClass("move");
            scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
            //当鼠标按下时计算鼠标与拖拽对象的距离
            disX = event.clientX + scrollLeft - obj.offsetLeft;
            disY = event.clientY + scrollTop - obj.offsetTop;

            obj.onmousemove = function (event) {
                //当鼠标拖动时计算div的位置
                var l = event.clientX - disX + scrollLeft;
                var t = event.clientY - disY + scrollTop;
                //console.log(l);
                //console.log(t);
                obj.style.left = l + "px";
                obj.style.top = t + "px";
            }
        };

        obj.onmouseup = function (event) {
            obj.onmousemove = null;//当鼠标弹起时移出移动事件
            //obj.onmouseup = null;//移出up事件，清空内存
            //检测是否普碰上，在交换位置
            //console.log(obj.style.left);
            var dist = 999999;
            var itemp = 99;
            var movePos = 99;
            for (var i = 0; i < itemPos.length; i++) {
                var mtop = obj.style.top;
                var mleft = obj.style.left;
                var itop = itemPos[i].top;
                var ileft = itemPos[i].left;
                mtop = mtop.substr(0, mtop.indexOf('px'));
                mleft = mleft.substr(0, mleft.indexOf('px'));
                itop = itop.substr(0, itop.indexOf('px'));
                ileft = ileft.substr(0, ileft.indexOf('px'));
                var dst = (mtop - itop) * (mtop - itop) + (mleft - ileft) * (mleft - ileft);
                if (dst>0&&dst < 80 * 80 && dst < dist) {
                    dist = dst;
                    itemp = i;
                }
                if (obj === items[i]) {
                    movePos = i;
                }
            }
            if (dist < 999999) {
                var setTop = itemPos[itemp].top;
                var setLeft = itemPos[itemp].left;
                asyncOrderby(obj,items[itemp]);
                items.splice(movePos, 1);
                var modItems = [];
                var pushNum=0;
                for (var i = 0; i < itemPos.length; i++) {
                    if (i === itemp) {
                        modItems.push(obj);
                    } else {
                        modItems.push(items[pushNum]);
                        pushNum++;
                    }
                }
                //console.log(modItems);
                for (var j = 0; j < itemPos.length; j++) {
                    modItems[j].style.left = itemPos[j].left;
                    modItems[j].style.top = itemPos[j].top;
                }
                items=modItems;

            }else{
                for (var j = 0; j < itemPos.length; j++) {
                    items[j].style.left = itemPos[j].left;
                    items[j].style.top = itemPos[j].top;
                }
            }
        }
    }
}

function asyncOrderby(move,to) {
    console.log("moveto",move,to);
    var fromId=$($(move).find(".page-id")[0]).val();
    var moveId=$($(to).find(".page-id")[0]).val();
    console.log(moveId);
    //console.log(toId);
    $.ajax({
        type:"post",
        url:base+"/adjustOrderBy",
        data:{"moveId":moveId,"fromId":fromId},
        async:true,
        success:function (data) {
            if (data==='success'){
                //alert();
            }
        }
    });

}