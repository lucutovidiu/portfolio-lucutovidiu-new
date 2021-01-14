async function deletePortfolioData(portfolioId) {
    return sendPostRequest("/api/portfolio/delete/" + portfolioId, {}, {}, 'DELETE');
}

async function sendPostRequest(url, formData, headers, method) {
    return new Promise((complete, err) => {
        fetch(url, {
            method: method,
            body: formData,
            headers
        })
            .then(response => response.json())
            .then(data => {
                if (data.succeed) {
                    complete(data);
                } else {
                    err(data)
                }
            })
            .catch(err => {
                err(err);
            });
    });
}
