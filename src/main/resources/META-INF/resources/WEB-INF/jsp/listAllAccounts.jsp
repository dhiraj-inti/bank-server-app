
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Existing accounts:</h1>
	<table class="table">
		<thead>
			<tr>
				<th>Account Number</th>
				<th>Balance</th>
				<th>User</th>
				<th></th>
				<th></th>
			</tr>
		</thead>

		<tbody>

			<c:forEach items="${accounts}" var="account">
				<tr>
					<td>${account.id}</td>
					<td>${account.balance}</td>
					<td>${account.user.fullName}</td>
					<td><button class="btn btn-danger"
						onclick="deleteAccount(${account.id})">DELETE</button></td>
					<td><a class="btn btn-warning"
						href="update-acct?id=${account.id}">UPDATE</a></td>
				</tr>
			</c:forEach>
		</tbody>


	</table>

	<a href="/add-acct" class="btn btn-success">Add Account</a>
</div>
<script>
function deleteAccount(accountId) {
	var baseUrl = '/api/accounts/';
    var dynamicUrl = baseUrl + accountId + '/delete';
	
    if (confirm('Are you sure you want to delete this account?')) {
        fetch(dynamicUrl, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            // Optionally, you can pass additional data in the request body
            // body: JSON.stringify({}),
        })
        .then(response => {
            // Parse the response body as JSON
            return response.json();
        })
        .then(data => {
            console.log(data); // JSON response
            if (data.success) {
                // Refresh the page or update UI as needed
                window.location.reload();
            } else {
                throw new Error('Failed to delete user');
            }
        })
        .catch(error => {
            console.error('Error deleting user:', error);
        });
    }
}
</script>

<%@ include file="common/footer.jspf"%>
