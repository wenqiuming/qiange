<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>枫页</title>
    <%
        String base = request.getContextPath();
        String username = (String) (request.getSession().getAttribute("username"));
    %>
    <script>
        var base = "<%=request.getContextPath()%>";
        var username = "<%=username%>";
    </script>
    <link rel="stylesheet" href="<%=base%>/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=base%>/static/css/nav.css">
    <link rel="icon" href="<%=base%>/static/img/icon.png">
    <script src="<%=base%>/static/jquery-2.2.3.min.js"></script>
    <script src="<%=base%>/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=base%>/static/js/nav.js"></script>
    <script src="<%=base%>/static/js/dragger.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse" id="cm-nav">
    <div class="container-fluid">
        <div class="cm-logo">
            <img src="<%=base%>/static/img/weblogo.png" id="cm-weblogo">
        </div>
        <div class="navbar-header">
            <a class="navbar-brand" href="<%=base%>/nav" id="cm-website-name">枫页</a>
        </div>


        <ul class="nav navbar-nav navbar-right">
            <div class="nav-search-box">
                <input placeholder="输入搜索内容" id="search-text"  class="search-text" tabindex="1">
                <a href="javascript:void(0)" class="text-info" onclick="doSearch()"><i class="glyphicon glyphicon-search"></i></a>
                <div class="search-select">
                    <ul class="list-group search-list">
                        <c:forEach items="${engines}" var="item">
                            <li class="list-group-item" tabindex="${item.orderby}"><a href="${item.url}"><img
                                    src="<%=base%>/static/img/engine/${item.searchLogo}">${item.name}</a>
                            </li>
                        </c:forEach>
                        <%--<li class="list-group-item" tabindex="2"><a href="https://www.baidu.com/s?wd="><img
                                src="<%=base%>/static/img/search-google.png">百度</a>
                        </li>--%>
                       <%-- <li class="list-group-item" tabindex="3"><a
                                href="https://www.google.com/search?source=hp&btnG=Google+搜索&q="><img
                                src="<%=base%>/static/img/search-google.png">google</a>
                        </li>
                        <li class="list-group-item" tabindex="4"><a
                                href="http://download.csdn.net/search/0/10/0/2/1/"><img
                                src="<%=base%>/static/img/search-google.png">csdn资源</a>
                        </li>--%>
                    </ul>
                </div>
            </div>

            <c:choose>
                <c:when test="${username==null ||username==''}">
                    <li><a href="javascript:void (0)" id="a-login"><span
                            class="glyphicon glyphicon-user login-icon"></span> 登录</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li style="margin-top: 17px;color: #ffffff"><b>${username}</b>
                    </li>
                    <li><a href="javascript:void (0)" id="a-logout" onclick="doLogout()"><span
                            class="glyphicon glyphicon-user login-icon"></span> 退出</a>
                    </li>
                </c:otherwise>
            </c:choose>

        </ul>
    </div>
</nav>
<div style="background-color: #fff;min-height: 50px">
    <div class="folder-toolbar">
        <div class="folder-boxs">
            <div class="picker"></div>
            <c:forEach items="${folders}" var="item">
                <div class="folder-container">
                    <div class="folder">
                        <input type="hidden" class="folder-id" value="${item.id}">
                        <a href="javascript:void(0)" class="folder-name">${item.fname}</a>

                        <div class="btn-folder-edit" onclick="showFolderEditMenu(this,event)">
                            <span class="glyphicon glyphicon-menu-hamburger span-folder-edit"></span>
                        </div>
                        <div class="folder-edit-dash">
                            <a href="javascript:void(0)"
                               onclick="showModFolderModal(event,this,${item.id},'${item.fname}')">修改</a><br>
                            <a href="javascript:void(0)" onclick="delFolder(this,${item.id})">删除</a>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <div class="clearfix"></div>
        </div>
        <div class="toolbar-right">
            <a href=" javascript:void(0)" id="edit" class="toolbar-edit"><span
                    class="glyphicon glyphicon-edit"></span></a>
            <a href=" javascript:void(0)" id="add" class="toolbar-add"><span
                    class="glyphicon  glyphicon-plus"></span></a>
        </div>
    </div>
</div>
<div class="tag-lib move-space">
</div>
<!-- login Modal -->
<div class="modal fade " id="loginModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content login-modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">登录账号</h4>
            </div>
            <div class="modal-body">
                <div class="cm-input-box"><span class="glyphicon glyphicon-user"></span><input type="text"
                                                                                               class="cm-input form-control"
                                                                                               id="username"
                                                                                               name="username"></div>
                <div class="cm-input-box"><span class="glyphicon glyphicon-lock"></span><input type="password"
                                                                                               class="cm-input  form-control"
                                                                                               id="password"
                                                                                               name="password"></div>
                <div>
                    <button class="btn btn-block btn-success" id="btn-login">登录</button>
                    <div class="div-to-reg"><a href="javascript:void(0)" onclick="showRegiste()">木有账号?去注册一个</a></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- registe Modal -->
<div class="modal fade " id="registeModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content login-modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">注册账号</h4>
            </div>
            <div class="modal-body">
                <div class="cm-input-box"><span class="glyphicon glyphicon-user"></span><input type="text"
                                                                                               class="cm-input form-control"
                                                                                               id="register"
                                                                                               name="username"></div>
                <div class="cm-input-box"><span class="glyphicon glyphicon-lock"></span><input type="password"
                                                                                               class="cm-input  form-control"
                                                                                               id="pwd"
                                                                                               name="password"></div>
                <div>
                    <button class="btn btn-block btn-info" id="btn-registe">注册</button>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!--add folder Modal -->
<div class="modal fade " id="addModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content login-modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">添加</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs add-ul">
                    <li class="active"><a data-toggle="tab" href="#addTagContainer">添加书签</a></li>
                    <li><a data-toggle="tab" href="#addFolderContainer">添加类目</a></li>
                </ul>
                <div class="tab-content">
                    <div id="addTagContainer" class="tab-pane  active">
                        <form class="form-horizontal" role="form" id="form-tag">
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="bgColor">类目:</label>
                                <div class="col-sm-10">
                                    <select type="text" class="form-control" name="folderId" id="select-fid">
                                        <c:forEach items="${folders}" var="item">
                                            <option value="${item.id}">${item.fname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="tname">书签名:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="tname" placeholder="如:百度" name="tname">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="url">地址:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="url"
                                           placeholder="如:https://www.baidu.com/" name="url">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="icon">图标:</label>
                                <div class="col-sm-10">
                                    <input type="file" class="form-control" id="icon" placeholder="可选" name="iconFile">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="icon_url">图标:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="icon_url"
                                           placeholder="图标地址" name="icon">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="bgColor">图标色:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="bgColor" placeholder="如:#0084ff"
                                           name="bgColor">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-10 col-sm-2">
                                    <button type="button" class="btn btn-default" id="btn-add-tag">添加</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="addFolderContainer" class="tab-pane fade">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="fname">类目:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="fname" placeholder="">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-10 col-sm-2">
                                    <button type="button" class="btn btn-default" id="btn-add-folder">添加</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- alert Modal -->
<div class="modal fade" id="alert-modal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">警告</h4>
            </div>
            <div class="modal-body text-center">
                <p id="alert-msg"></p>
            </div>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>--%>
        </div>
    </div>
</div>

<!-- modModal -->
<div id="modMemberModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改书签</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="mod-tag-form">
                    <input type="hidden" name="tagId" id="mod_tagId">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="bgColor">类目:</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="folderId" id="mod_fid">
                                <c:forEach items="${folders}" var="item">
                                    <option value="${item.id}">${item.fname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="tname">书签名:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mod_tname" name="tname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="url">地址:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mod_url" name="url">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mod_icon">图标:</label>
                        <div class="col-sm-10">
                            <input type="file" class="form-control" id="mod_icon" name="iconFile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mod_icon_url">图标:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mod_icon_url" name="icon">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="bgColor">图标色:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mod_bgColor"
                                   name="bgColor">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-10 col-sm-2">
                            <button type="button" class="btn btn-default" id="btn-mod-tag">修改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- modFolderModal -->
<div id="modFolderModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改类目</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mod_fname">书签名:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mod_fname" name="fname">
                        </div>
                        <input type="hidden" id="mod_folder_id">
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-10 col-sm-2">
                            <button type="button" class="btn btn-default" id="btn-mod-folder">修改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
