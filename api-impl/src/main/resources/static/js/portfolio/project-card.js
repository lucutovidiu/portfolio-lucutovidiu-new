$(document).ready(function () {
})

async function deletePortfolio(portfolioId) {
    let confirmation = confirm("Are you sure you want to delete portfolio id: " + portfolioId)
    if (confirmation) {
        try {
            let result = await deletePortfolioData(portfolioId);
        } catch (exception) {
            console.log(exception);
        } finally {
            confirmDeletion();
        }
    }
}

function confirmDeletion() {
    alert("Portfolio deleted");
    location.reload();
}