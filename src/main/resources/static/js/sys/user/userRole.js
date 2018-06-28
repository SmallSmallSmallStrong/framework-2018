
function getData( data , id) {
    for (var i = 0; i < data.length; i += 6) {
        var html = '<tr>';
        for (var j = 0; j < 6; j++) {
            if (data[i + j] == undefined) {
                html += '<td>\
                                <div class="tdDiv">\
                                </div>\
                             </td>';
            } else {
                var tmp = "";
                console.log(data[i + j].ischecked);
                if (!!data[i + j].ischecked) {
                    tmp = ' checked';
                }
                html += '<td>\
                                <div class="tdDiv">\
                                    <input name="rids" id="a' + i + '" type="radio"  value="' + data[i + j].id + '" ' + tmp + '/>' +
                    '<label for="a' + i + '">' + data[i + j].name + '</label>\
                             </div>\
                          </td>';
            }
        }
        html += '</tr>';
        $("#tableDiv").append(html);
        $("#tableDiv").append("<input type=\"hidden\" name='id' value=\"" + id + "\" >");
        $("#tableDiv").append("<input class=\"btn btn-primary radius\" style=\"margin:5px 0 0 5px\"  type=\"submit\"  value=\"保存\" >");
    }
}