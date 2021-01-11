let postJsonRequest =
    function postJson(url, msg) {
        return new Promise(function (resolve, reject) {
            let success;
            let data;
            let status;
            let errorThrown;

            $.ajax({
                url: url,
                dataType: 'json',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(msg),
                processData: false,
                success: function (receivedData, statusCode, jQxhr) {
                    success = true;
                    data = receivedData;
                    status = statusCode;
                    resolve({success, data, status});
                },
                error: function (receivedData, statusCode, thrownError) {
                    success = false;
                    data = receivedData;
                    status = statusCode;
                    errorThrown = thrownError;
                    reject({success, data, status, errorThrown});
                }
            });
        })
    };