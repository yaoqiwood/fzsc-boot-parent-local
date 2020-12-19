const initSyncInfRecordUrl = HTTP + BAISC_URL + BAISC_CONTEXT + '/ptype/initPtypeSyncPage';

$(document).ready(() => {
    initSyncInfRecord()
});

function initSyncInfRecord() {
    $.ajax({
        url: initSyncInfRecordUrl,
        contentType: "application/json;charset=UTF-8",
        type: 'post',
        dataType: 'json',
        success(resp) {
            console.log(resp)
        }
    })
}
