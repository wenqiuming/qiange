$(document).ready(function () {
    hei = 48;
    editState = true;
    firstFd = $(".folder")[0];
    if (firstFd) {
        $(firstFd).addClass("folder-active");
    }
    setPick();
    $(".folder-container").hover(function () {
        var swidth = $(this).find(".folder").css("width");
        var sleft = $(this).position().left;
        var sheight = $(this).position().top;
        $(".picker").css({"width": swidth, "left": sleft, "top": sheight + hei});
    }, function () {
        setPick();
    });


    $(".folder").click(function () {
        $(".folder").removeClass("folder-active");
        $(this).addClass("folder-active");
        setPick();
        getTagList();
    });

    /*查询第一个文件夹内容*/
    getTagList();

    $("#a-login").click(function () {
        toggleLogin();
    });
    $("#btn-login").click(function () {
        doLogin();
    });
    $("#btn-add").click(function () {
        addFolder();
    });
    $("#btn-add-tag").click(function () {
        addTag();
    });
    $("#btn-add-folder").click(function () {
        addFolder();
    });
    $("#add").click(function () {
        add();
    });
    $("#btn-registe").click(doRegiste);
    $("#btn-login").click(doLogin);

    $(window).click(function (ele) {
        var cname = $(ele.target).attr("class");
        if (!cname || cname.indexOf("span-member-edit") === -1) {
            $(".edit-dash").fadeOut(0);
        }
        if (!cname || cname.indexOf("search-text") === -1) {
            $(".search-select").fadeOut();
        }
        if (!cname || cname.indexOf("span-folder-edit") === -1) {
            $(".folder-edit-dash").fadeOut();
        }

    });
    $("#edit").click(doEdit);

    $("#btn-mod-folder").click(function () {
        var id = $("#mod_folder_id").val();
        var fname = $("#mod_fname").val();
        if (fname === null || fname.trim() === "") {
            $("#alert-msg").html("请填写类目名");
            $("#alert-modal").modal("show");
        } else {
            $.ajax({
                type: "post",
                url: base + "/modFolder",
                data: {"id": id, "fname": fname},
                async: true,
                success: function () {
                    window.location.reload();
                }
            });
        }
    });
    $("#btn-mod-tag").click(modTag);

    $("#search-text").focus(function () {
        //console.log("fouces");
        $(".search-select").fadeIn();
    });

    $(".nav-search-box").keydown(function (e) {
        if (e.keyCode === 13) {
            //  alert();
            //$($(".search-select li")[1]).focus();
            doSearch();
        }
        if (e.keyCode === 38) {
            moveFocus(true);
        }
        if (e.keyCode === 40) {
            moveFocus(false);
        }
    });
    $(".search-select li").focus(function () {
        //console.log(this);
        $(".search-select li").removeClass("search-focus");
        $(this).addClass("search-focus");
    });
    $(".search-select li a").click(function (e) {
        e.preventDefault();
        $(".search-select li").removeClass("search-focus");
        $($(this).parents(".search-select li")[0]).addClass("search-focus");
        doSearch();
    });
    $(".search-select li").hover(function () {
        $(".search-select li").removeClass("search-focus");
        $(this).addClass("search-focus");
    }, function () {
        $(this).removeClass("search-focus");
    });
    setTabIndex();

});

function setPick() {
    var activeFolder = $(".folder.folder-active");
    //console.log(activeFolder);
    if (activeFolder.length > 0) {
        var pwidth = $(activeFolder).css("width");
        var pleft = $(activeFolder).position().left;
        var pheight = $(activeFolder).position().top;
        $(".picker").css({"width": pwidth, "left": pleft, "top": pheight + hei});
    } else {
        $(".picker").css({"width": 0, "left": 0, "top": hei});
    }
}

function getTagList() {
    var fid = $(".folder-active>.folder-id").val();
    //console.log("fid",fid);
    if (fid) {
        $.ajax({
            type: "post",
            url: base + "/queryTagsByFolder",
            data: {'folderId': fid},
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            async: true,
            success: function (data) {
                // console.log("data",data);
                addTabToView(data);
                resetEdit();
                dragger();
            }
        });//end ajax
    }

}

function addTabToView(data) {
    $(".tag-lib").children().remove();
    for (var i = 0; i < data.length; i++) {
        var id = data[i].id;
        var tname = data[i].tname;
        var icon = data[i].icon;
        var url = data[i].url;
        var type = data[i].type;
        var bgColor = data[i].bgColor;
        var topName = data[i].topName;
        if (type == 1) {
            topName = "";
        }
        if (icon == null) {
            icon = '';
        }
        var obj = JSON.stringify(data[i]).replace(/\"/g,"'");
        console.log(obj);
        var member = '' +
            '<div class="member-container">' +
            '<input type="hidden" class="page-id" value=' + id + '>' +
            '<div class="btn-member-edit" onclick="showEditMenu(this)">' +
            '<span class="glyphicon glyphicon-menu-hamburger span-member-edit"></span>' +
            '</div>' +
            '<div class="edit-dash">' +
            ' <a href="javascript:void(0)" onclick="showModeModal(' + obj + ')">修改</a><br>' +
            '<a href="javascript:void(0)" onclick="delTag(this,' + id + ')">从列表中移除</a>' +
            '</div>' +
            '<a href="' + url + '">' +
            '<div class="member-body">' +
            '<div class="member-logo" ' +
            'style="background-image: url(' + icon + ');' +
            'background-color: ' + bgColor + '">' +
            ' <b class="member-name">' + topName + '</b>' +
            '</div>' +
            '</div>' +
            '</a>' +
            '<div class="member-footer">' + tname + '</div>' +
            '</div>';
        $(".tag-lib").append(member);
    }
    $(".tag-lib").append('<div class="clearfix"></div>');
}

function toggleLogin() {
    $("#loginModal").modal("show");
}

function doLogin() {
    var usr = $("#username").val();
    var pwd = $("#password").val();
    $.ajax({
        type: "post",
        url: base + "/doLogin",
        data: {'username': usr, 'password': pwd},
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        async: false,
        success: function (data) {
            if (data === 'success') {
                window.href = base + "/nav";
            } else {
                alert("账号或密码错误--");
            }
        }
    });//end ajax
}

function add() {
    if (!username || username === '' || username === 'null') {
        $("#alert-msg").html("请登录账号");
        $("#alert-modal").modal("show");
        return;
    }
    $("#addModal input").val("");
    var activeFolder = $(".folder.folder-active");
    if (activeFolder) {
        var activeFid = $(activeFolder).find(".folder-id").val();
        $("#select-fid").val(activeFid);
    }
    $("#addModal").modal("show");
}

function addFolder() {
    var fname = $("#fname").val();
    if (fname === null || fname === "") {
        $("#alert-msg").html("请填写目录名称");
        $("#alert-modal").modal("show");
    } else {
        $.ajax({
            type: "post",
            url: base + "/addFolder",
            data: {'fname': fname},
            async: false,
            success: function (data) {
                if (data === 'success') {
                    window.location.reload();
                } else {
                    $("#addModal").modal("hide");
                    $("#alert-msg").html("添加失败,请登录账号");
                    $("#alert-modal").modal("show");
                }
            }
        });
    }
}

function addTag() {
    var tname=$("#tname").val();
    if (tname === null || tname === "") {
        $("#alert-msg").html("请填写书签名称");
        $("#alert-modal").modal("show");
    } else {
        var formData = new FormData($("#form-tag")[0]);
        $.ajax({
            url: base + "/addTag",
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if ('success' === data) {
                    getTagList();
                    $("#addModal").modal("hide");
                }
                else if (!data || data == "") {
                    $("#alert-msg").html("添加失败,请登录账号");
                    $("#addModal").modal("hide");
                    $("#alert-modal").modal("show");
                }
                else {
                    $("#alert-msg").html(data);
                    $("#alert-modal").modal("show");
                }
            }
        });
    }
}

function showModeModal(data) {
    var id = data.id;
    var tname = data.tname;
    var icon = data.icon;
    var url = data.url;
    var type = data.type;
    var bgColor = data.bgColor;
    var folderId = data.folderId;
    $("#mod_fid").val(folderId);
    $("#mod_tagId").val(id);
    $("#mod_icon_url").val(icon);
    $("#mod_url").val(url);
    $("#mod_tname").val(tname);
    $("#mod_bgColor").val(bgColor);
    $("#mod_icon").val("");
    $("#modMemberModal").modal("show");
}

function showEditMenu(ele) {
    var dashborad = $(ele).siblings(".edit-dash")[0];
    $(dashborad).fadeIn(.5);
}

function showFolderEditMenu(ele, event) {
    var dashborad = $(ele).siblings(".folder-edit-dash")[0];
    $(dashborad).fadeIn(.5);
    event.stopPropagation();
}

function showModFolderModal(event, ele, val, fname) {
    event.stopPropagation();
    //console.log(event);
    $("#modFolderModal").modal();
    $("#mod_folder_id").val(val);
    $("#mod_fname").val(fname);
}

function doEdit() {
    if (!username || username === '' || username === 'null') {
        $("#alert-msg").html("请登录账号");
        $("#alert-modal").modal("show");
        return;
    }

    if (editState) {
        $(".toolbar-right  .toolbar-edit").css("color","blue");
        $(".btn-folder-edit").css("display", "inline-block");
        $(".btn-member-edit").css("display", "inline-block");
        $(".member-container a").attr("href", "javascript:void(0)");
    } else {
        $(".toolbar-right  .toolbar-edit").css("color","#E8AA8C");
        $(".btn-folder-edit").css("display", "none");
        $(".btn-member-edit").css("display", "none");
        getTagList();
    }
    editState = !editState;
}

function resetEdit() {
    if (!editState) {
        $(".btn-mod-folder").css("display", "inline-block");
        $(".btn-del-folder").css("display", "inline-block");
        $(".btn-member-edit").css("display", "inline-block");
    }
}

function delFolder(ele, id) {
    $.ajax({
        type: "post",
        url: base + "/delFolder",
        data: {"id": id},
        async: false,
        success: function () {
            // var clazz = $(ele).parents('.folder').attr('class');
            // if (clazz.indexOf("folder-active") >= 0) {
            //     $($(".folder")[0]).click();
            // } else {
            //     setPick();
            // }
            // $($(ele).parents(".folder-container")).remove();
            // setPick();
            window.location.reload();
        }
    });
}

function delTag(ele, id) {
    $.ajax({
        type: "post",
        url: base + "/delTag",
        data: {"id": id},
        async: false,
        success: function () {
            //$($(ele).parents(".member-container")[0]).remove();
            getTagList();
        }
    });
}

function modTag() {
    var formData = new FormData($("#mod-tag-form")[0]);
    $.ajax({
        url: base + "/modTag",
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            //console.log(data);
            $("#modMemberModal").modal("hide");
            if ('success' === data) {
                getTagList();
            } else {
                $("#alert-msg").html("修改失败,请重试~");
                $("#alert-modal").modal("show");
            }
        }
    });
}

function doRegiste() {
    var username = $('#register').val();
    var password = $("#pwd").val();
    if (!username || username.trim().length === 0) {
        $("#alert-msg").html("请录入用户名");
        $("#alert-modal").modal("show");
    }
    if (!password || password.trim().length === 0) {
        $("#alert-msg").html("请录入密码");
        $("#alert-modal").modal("show");
    }
    $('#btn-registe').attr("disabled","disabled");
    $.ajax({
        type: "post",
        url: base + "/doRegiste",
        async: false,
        data: {"password": password, "username": username},
        success: function (data) {
            $('#btn-registe').removeAttr("disabled");
            if ("success" === data) {
                $("#registeModal").modal("hide");
                $("#alert-msg").html("注冊成功,请登录");
                $("#alert-modal").modal("show");
            } else {
                $("#alert-msg").html(data);
                $("#alert-modal").modal("show");
            }
        }
    });
}

function showRegiste() {
    $("#loginModal").modal("hide");
    $("#registeModal").modal("show");
}

function doLogin() {
    var username = $('#username').val();
    var password = $("#password").val();
    if (!username || username.trim().length === 0) {
        $("#alert-msg").html("请录入用户名");
        $("#alert-modal").modal("show");
    }
    if (!password || password.trim().length === 0) {
        $("#alert-msg").html("请录入密码");
        $("#alert-modal").modal("show");
    }
    $.ajax({
        type: "post",
        url: base + "/doLogin",
        async: false,
        data: {"password": password, "username": username},
        success: function (data) {
            if ("success" === data) {
                $("#registeModal").modal("hide");
                window.location.reload();
            } else {
                $("#alert-msg").html("用户名或密码错误");
                $("#alert-modal").modal("show");
            }
        }
    });
}

function doLogout() {
    $.ajax({
        type: "post",
        url: base + "/doLogout",
        async: false,
        success: function (data) {
            if ("success" === data) {
                window.location.reload();
            } else {
                $("#alert-msg").html("操作异常");
                $("#alert-modal").modal("show");
            }
        }
    });
}

var engine = "https://www.baidu.com/s?wd=";

/*search*/
function doSearch() {
    var searchEngine = $(".search-select li.search-focus a")[0];
    if (searchEngine) {
        engine = $(searchEngine).attr("href");
    }
    var keyword = $("#search-text").val();
    window.location.href = engine + keyword;
}

function moveFocus(isUp) {
    var items = $(".search-select li");
    var focusItem = $(".search-select li.search-focus");
    if (focusItem.length > 0) {
        var point = 0;
        for (var i = 0; i < items.length; i++) {
            if (focusItem[0] === items[i]) {
                point = i;
                break;
            }
        }
        if (isUp) {
            point = ((point - 1) < 0 ? (items.length - 1) : point - 1);
        } else {
            //console.log("point", point);
            //console.log("length", items.length);
            point = ((point + 1) > (items.length - 1) ? 0 : point + 1);
        }
        $(items[point]).focus();
    } else {
        $(items[0]).focus();
    }
}

function setTabIndex() {
    var alinks = $("a");
    for (var i = 0; i < alinks.length; i++) {
        var tindex = $(alinks[i]).attr("tabindex");
        if (tindex && tindex !== '0') {
        } else {
            $(alinks[i]).attr("tabindex", "-1");
        }
    }
}