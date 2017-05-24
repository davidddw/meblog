<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>后台管理页面</title>
    <link rel="shortcut icon" href="${basePath}/static/favicon.ico"/>
    <!-- CDN start -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="https://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/datatables/1.10.13/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/toastr.js/2.1.3/toastr.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/nprogress/0.2.0/nprogress.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/css/fileinput.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/themes/explorer/theme.min.css">
    <!-- CDN end-->
    <!-- Custom Theme Style -->
    <link rel="stylesheet" href="${basePath}/static/admin/css/custom.css">
    <link rel="stylesheet" href="${basePath}/static/admin/css/user-manage.css">
</head>
<body class="nav-md">
<div class="container body" id="crop-avatar">
    <div class="main_container">
    <#include "sidebar.ftl">
    <#include "navigation.ftl">
        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>小说管理 </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row-fluid">
                                    <div class="pull-right">
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-primary btn-sm" id="btn-add"
                                                    data-toggle="modal" data-target=".bs-example-modal-md">
                                                <i class="fa fa-plus"></i> 添加小说
                                            </button>
                                            <button type="button" class="btn btn-primary btn-sm" id="btn-delAll">
                                                <i class="fa fa-remove"></i> 批量删除
                                            </button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-2">
                                            <input type="text" id="keyword" name="keyword" class="form-control input-sm"
                                                   placeholder="小说名称">
                                        </div>
                                        <button type="button" class="btn btn-primary btn-sm" id="btn-query">
                                            <i class="fa fa-search"></i> 查询
                                        </button>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span12" id="div-table-novel">
                                        <table class="table table-striped table-bordered table-hover dt-responsive nowrap" id="table-novel"
                                               cellspacing="0" width="100%">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <input type="checkbox" name="cb-check-all" class="iCheck">
                                                </th>
                                                <th>编号</th>
                                                <th>名称</th>
                                                <th>作者</th>
                                                <th>类型</th>
                                                <th>描述</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- modals -->
                <div class="modal fade bs-example-modal-md" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                </button>
                                <h4 class="modal-title" id="myModalLabel">添加小说</h4>
                            </div>
                            <div class="modal-body1 info-content">
                                <div class="block-content ">
                                    <form id="form_add" enctype="multipart/form-data">
                                        <input type="hidden" name="_method" value="put" />
                                        <input type="text" style="display:none" id="novel_id"  />
                                        <div class="control-group">
                                            <label class="control-label" for="extn-add">
                                                <span class="red-asterisk">*</span>名称:</label>
                                            <div class="controls">
                                                <input type="text" class="xlarge" id="novel_name" name="extn-add" placeholder="小说名称" required>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="name-add">
                                                <span class="red-asterisk">*</span>作者:</label>
                                            <div class="controls">
                                                <input type="text" class="xlarge" id="novel_author" name="name-add" required placeholder="作者">
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="name-add">
                                                <span class="red-asterisk">*</span>描述:</label>
                                            <div class="controls">
                                                <input type="text" class="xlarge" id="novel_description" name="name-add" required placeholder="描述">
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="name-add">
                                                <span class="red-asterisk">*</span>类型:</label>
                                            <div class="controls">
                                                <select id="novel_type" class="form-control xlarge" required>
                                                    <option value="玄幻奇幻">玄幻奇幻</option>
                                                    <option value="武侠仙侠">武侠仙侠</option>
                                                    <option value="都市言情">都市言情</option>
                                                    <option value="历史军事">历史军事</option>
                                                    <option value="游戏竞技">游戏竞技</option>
                                                    <option value="科幻灵异">科幻灵异</option>
                                                    <option value="其他类型">其他类型</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="name-add">
                                                <span class="red-asterisk">*</span>封面:</label>
                                            <div class="controls">
                                                <input id="avatar" name="avatar" type="file" class="file-loading" accept="image">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-close"></i> 关闭</button>
                                            <button type="button" class="btn btn-primary" id="submit_novel"><i class="fa fa-check"></i> 保存更改</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade bs-example1-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                </button>
                                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                            </div>
                            <div class="modal-body info-content">
                                <div class="block-content ">
                                    <div class="container kv-main">
                                        <div class="row ">
                                            <label class="control-label">Select File</label>
                                            <input id="file-0a" name="file" type="file" multiple class="file-loading" accept="image">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->
    <#include "footer.ftl">
        <!-- footer content -->
    </div>
</div>
<!-- CDN start -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
<script src="https://cdn.bootcss.com/parsley.js/2.7.0/parsley.min.js"></script>
<script src="https://cdn.bootcss.com/datatables/1.10.13/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.bootcss.com/datatables/1.10.13/js/dataTables.bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/toastr.js/2.1.3/toastr.min.js"></script>
<script src="https://cdn.bootcss.com/nprogress/0.2.0/nprogress.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/js/fileinput.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/js/locales/zh.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/themes/explorer/theme.min.js"></script>
<!-- lhgdialog -->
<script src="${basePath}/static/admin/js/lhgdialog/lhgdialog.js"></script>
<!-- Custom Theme Scripts -->
<#--<script src="${basePath}/static/admin/js/custom.js"></script>-->
<script src="${basePath}/static/admin/js/pestle.js"></script>
</body>
</html>