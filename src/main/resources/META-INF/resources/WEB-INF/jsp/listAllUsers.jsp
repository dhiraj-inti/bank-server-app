
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Existing users:</h1>
	<table class="table">
		<thead>
			<tr>
				<th>User ID</th>
				<th>Username</th>
				<th>Full Name</th>
				<th>Contact</th>
				<th></th>
				<th></th>
			</tr>
		</thead>

		<tbody>

			<c:forEach items="${users}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.fullName}</td>
					<td>${user.contact}</td>
					<td><button class="btn btn-danger" 
						onclick="deleteUser(${user.id})">DELETE</button></td>
					<td><a class="btn btn-warning"
						href="update-user?id=${user.id}">UPDATE</a></td>
				</tr>
			</c:forEach>
		</tbody>


	</table>

	<a href="/add-user" class="btn btn-success">Add User</a>
</div>
<script>
function deleteUser(userId) {
	var baseUrl = '/api/users/';
    var dynamicUrl = baseUrl + userId + '/delete';
	
    if (confirm('Are you sure you want to delete this user?')) {
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
            //console.log(data); // JSON response
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
