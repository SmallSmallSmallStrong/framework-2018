/**
 * 公用js
 */

/** 返回上一页 */
function setNo() {
	history.back(-1);
}

/** 全选 */
function selectAll(groupId, className, obj) {
	$('#' + groupId + ' .' + className).prop('checked', $(obj).prop('checked'));
}

/** 多条操作 */
function manageMulti(groupId, className, url, msg) {

	var selObjs = $('#' + groupId + ' .' + className + ':checked');
	var selSize = selObjs.size();
	if (selSize == 0) {
		layer.alert('请至少选择一项来操作。');
	} else {
		if (msg != null && msg.length > 0) {
			layer.confirm(msg, function(index) {
				joinUrlAddJump(url, selSize, selObjs);
			});
		} else {
			joinUrlAddJump(url, selSize, selObjs);
		}
	}
}

/** 拼接url并跳转（/admin/function/delete?ids=1,2,3） */
function joinUrlAddJump(url, selSize, selObjs) {
	var ids = '';
	for (var i = 0; i < selSize; i++) {
		if (i != 0)
			ids += ',';
		ids += selObjs[i].value;
	}
	window.location = url + ids;
}

/** 提示 */
function manageConfirm(url, msg) {
	if (msg != null && msg.length > 0) {
		layer.confirm(msg, function(index) {
			window.location = url;
		});
	} else {
		window.location = url;
	}
}

/** 翻页 */
function pageNext(curentPage) {
	var tmpPage = $.trim(curentPage);
	if (tmpPage != '') {
		$('#curentPage').val(tmpPage);
	} else {
		var pageT = $.trim($('#searchPage').val());
		if (pageT != '') {
			if (!isNaN(pageT)) {
				$('#curentPage').val(pageT);
			} else {
				$('#curentPage').val(1);
			}
		} else {
			$('#curentPage').val(1);
		}
	}
	$('#searchForm').submit();
}

/** 修改iframe的src */
function changeIframe(name, url) {
	$("#Hui-tabNav .Hui-tabNav-wp #min_title_list li.active span").text(name);
	$("#iframe_box .show_iframe #adminIframeId").prop("src", url);
}

/** 添加-修改 */
function add(name, url) {
	var iframeParent = parent.document.getElementById("adminIframeId");
	var titleParent = parent.document.getElementById("min_title_list");
	$(titleParent).children("li").children("span").text(name);
	$(iframeParent).prop("src", url);
}
/** 弹出框 输入内容 */
function addReason(url) {
	layer.prompt({
		formType : 2,
		value : '',
		title : '请输入不通过原因',
		area : [ '500px', '250px' ]
	// 自定义文本域宽高
	}, function(value, index, elem) {
		window.location = url + '&reason=' + value;
		layer.close(index);
	});
}
/** 显示审核未通过原因 */
function showReason(reason) {
	layer.open({
		title : '未通过原因',
		content : reason
	});
}
/** 跳转至指定页 */
function jump() {
	var pageNum = $("#search").val();
	if (isNaN(parseInt(pageNum)) || parseInt(pageNum) < 1) {
		pageNum = 1;
	}
	pageNext(pageNum);
}
//加入收藏
function AddFavorite(sURL, sTitle) {
	sURL = encodeURI(sURL);
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch (e) {
			alert("加入收藏失败，请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");
		}
	}
}
//设为首页
function SetHome(url) {
	if (document.all) {
		document.body.style.behavior = 'url(#default#homepage)';
		document.body.setHomePage(url);
	} else {
		alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!");
	}
}
function getTime() {
	var oDate = new Date(); //实例一个时间对象；
	oDate.getFullYear(); //获取系统的年；
	oDate.getMonth() + 1; //获取系统月份，由于月份是从0开始计算，所以要加1
	oDate.getDate(); // 获取系统日，
	return oDate.getFullYear() + '年' + (oDate.getMonth() + 1) + '月'
			+ oDate.getDate() + '日';
}
function getWeek() {
	var oDate = new Date(); //实例一个时间对象；
	var weekStr = '';
	switch (oDate.getDay()) {
	case 0:
		weekStr = '日';
		break;
	case 1:
		weekStr = '一';
		break;
	case 2:
		weekStr = '二';
		break;
	case 3:
		weekStr = '三';
		break;
	case 4:
		weekStr = '四';
		break;
	case 5:
		weekStr = '五';
		break;
	case 6:
		weekStr = '六';
		break;
	}
	return '星期' + weekStr;
}