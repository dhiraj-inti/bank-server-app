
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Add Account</h1>
	<form id="addUserForm">
		<div class="mb-3">
			<label class="form-label">Username</label>
			<input type="text" class="form-control"
				name="username" required="required" />
			
		</div>
		<div class="mb-3">
			<label class="form-label">Password</label>
			<input type="text" class="form-control"
				name="password" required="required" />
		</div>
		<div class="mb-3">
			<label class="form-label">Full Name</label>
			<input type="text" class="form-control"
				name="fullName" required="required" />
		</div>
		<div class="mb-3">
			<label class="form-label">Contact</label>
			<input type="text" class="form-control"
				name="contact" required="required" />
		</div>

		<button type="submit" class="btn btn-primary">Submit</button>
	</form>

</div>

<script>
    document.getElementById("addUserForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission
        
        // Get form data
        const formData = new FormData(this);
        
        // Convert form data to JSON object
        const jsonObject = {};
        formData.forEach(function(value, key) {
            jsonObject[key] = value;
        });
        
        
        // Make Fetch API call to submit form data
        fetch('/api/users/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonObject)
        })
        .then(response => {
            // Parse the response body as JSON
            return response.json();
        })
        .then(data => {
            if (data.success) {
                // Redirect to a success page or handle success response
                alert('Created user successfully');
            } else {
                // Handle error response
                alert('Failed to create user');
                console.error('Failed to create user');
            }
            window.location.href = '/list-users';
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
</script>

<%@ include file="common/footer.jspf"%>