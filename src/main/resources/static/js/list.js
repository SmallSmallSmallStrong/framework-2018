// <div class="page" id="layuipage"></div>
//     <!--此处的id不可修改因为showPage需要用到-->
// <div th:insert="~{base/footer::foot}"></div>
// <script th:inline="javascript">
//     //分页函数，参数一为url，参数二为具体数据
//     $(function () {
//         showPage([[${LIST_URL}]], [[${page}]]);
//     });
// </script>

/**
 *
 *
 * @param url
 * @param page
 */
function showPage(url, page) {
    layui.use(['laypage'], function () {
        var laypage = layui.laypage;
        /*var url = url;
        var page = page;*/
        var parm = $('#searchForm input');
        var strparm = "";
        for (var i = 0; i < parm.length; i++) {
            strparm += "&" + parm[i].name + "=" + parm[i].value;
        }
        laypage.render({
            elem: 'layuipage'//div的id
            , count: page.totalElements //总数
            , limit: page.size //每页显示条数
            , layout: ['count', 'prev', 'page', 'next', ' refresh', 'skip']
            //layout : 自定义排版。可选值有：count（总条目输区域）、prev（上一页区域）、
            // page（分页区域）、next（下一页区域）、limit（条目选项区域）、
            // refresh（页面刷新区域。注意：layui 2.3.0 新增） 、skip（快捷跳页区域）
            , curr: page.number + 1 //當前頁
            , theme: '#41a26f'//自定义主题
            , hash: 'page'
            , jump: function (obj, first) {
                // console.log(parm);
                if (first != true) {//判断不是第一次加载
                    // console.log(url + "?page=" + (obj.curr - 1) + strparm);
                    window.location = url + "?page=" + (obj.curr - 1) + strparm;
                }
            }
        });

    });
}
