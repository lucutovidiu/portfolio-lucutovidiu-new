const deletePortfolioUrl = "/api/portfolio/delete/";

async function deletePortfolioData(portfolioId) {
    try {
        return await fetch(deletePortfolioUrl + portfolioId, {method: 'DELETE'});
    } catch (e) {
        console.log(e)
    }
}
