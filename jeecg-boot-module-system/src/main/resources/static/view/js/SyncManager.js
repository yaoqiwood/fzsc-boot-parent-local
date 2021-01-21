const initSyncInfRecordUrl = HTTP + BAISC_URL + BAISC_CONTEXT + '/ptype/initPtypeSyncPage';
const hisPtypeSyncTableRecordUrl = HTTP + BAISC_URL + BAISC_CONTEXT + '/hisPtypeSync/data-grid.json';
let pageParams = {
    pageNo: 0,
    pageSize: 5,
    sortBy: "",
    order: ""
};


$(document).ready(() => {
    initSyncInfRecord();
    initSyncInfTableData();
});

function initSyncInfTableData() {
    var columns = [
        {name: 'remoteHost', label: '远程地址', width: 132},
        {name: 'receiveTime', label: '接受数据时间', width: 134},
        {name: 'updatePriceTime', label: '同步更新价格日期', width: 109},
        {name: 'updateTag', label: '更新Tag', width: 109},
        {name: 'status', label: '状态', width: 200},
        {name: 'createTime', label: '更新时间', width: 200}
    ];
    $("#dataTableGrid thead").append("<tr></tr>");
    $("#dataTableGrid thead tr").append("<th name='index'> 序号 </th>");
    for (const element of columns) {
        $("#dataTableGrid thead tr").append("<th name=" + element.name + ">" + element.label + "</th>")
    }
    fillTableData();

}

function fillTableData() {
    httpUtilGet(hisPtypeSyncTableRecordUrl, pageParams).then(resp => {
        let index = (resp.pager.pageNo - 1) * resp.pager.pageSize;
        $("#dataTableGrid tbody").empty();
        for (const respElement of resp.data) {
            $("#dataTableGrid tbody").append("<tr>" +
                "<td>" + (index++) + "</td>" +
                "<td>" + trimNullText(respElement.remoteHost) + "</td>" +
                "<td>" + trimNullText(respElement.receiveTime) + "</td>" +
                "<td>" + trimNullText(respElement.updatePriceTime) + "</td>" +
                "<td>" + trimNullText(respElement.updateTag) + "</td>" +
                "<td>" + (respElement.status == 'price' ? '价格更新' : respElement.status == 'ptype' ? '商品种类更新' : '') + "</td>" +
                "<td>" + trimNullText(respElement.createTime) + "</td>" +
                +"</tr>"
            );
        }
        // 手动进行初始化
        $('#pager').page({
            totalPages: (parseInt(resp.pager.total / resp.pager.pageSize) + (resp.pager.total % resp.pager.pageSize > 0 ? 1 : 0)),
            initPage: resp.pager.pageNo,
            liNums: 7,
            activeClass: 'activP', //active 类样式定义
            callBack: function (page) {
                console.log(page);
                pageParams.pageNo = page;
                fillTableData();
            }
        });
    });
}

function trimNullText(val) {
    if (val == null) {
        return ''
    }
    return val;
}

function httpUtilGet(url, params) {
    return new Promise((resolve, reject) => {
        $.get({
            url: url,
            headers: {
                "X-Access-Token": localStorage.getItem("token")
            },
            data: params,
            success(resp) {
                resolve(resp)
            }
        })
    });
}


function initSyncInfRecord() {
    $.ajax({
        url: initSyncInfRecordUrl,
        contentType: "application/json;charset=UTF-8",
        headers: {
            "X-Access-Token": localStorage.getItem("token")
        },
        type: 'post',
        dataType: 'json',
        success(resp) {
            if (resp.success) {
                $("#lastTimePtypeUpdate").html(resp.result.maxUpdateTagRecord.createTime);
                $("#lastTimePriceUpdate").html(resp.result.maxUpdatePriceTimeRecord.updatePriceTime);
            } else {
                localStorage.setItem("token", "");
                $(location).attr("href", "./LoginPage.html")
            }
        },
        error(resp) {
            $(location).attr("href", "./LoginPage.html")
        }
    })
}
