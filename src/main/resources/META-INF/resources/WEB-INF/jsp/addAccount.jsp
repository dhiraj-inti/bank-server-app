
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Add Account</h1>
	<form id="addAccountForm">
		<div class="mb-3">
			<label class="form-label">Balance</label>
			<input type="number" class="form-control"
				name="balance" required="required" />
			
		</div>
		<div class="mb-3">
			<label class="form-label">User ID</label>
			<input type="number" class="form-control"
				name="userId" required="required" />
		</div>

		<button type="submit" class="btn btn-primary">Submit</button>
	</form>

</div>

<script>
    document.getElementById("addAccountForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission
        
        // Get form data
        const formData = new FormData(this);
        
        // Convert form data to JSON object
        const jsonObject = {};
        formData.forEach(function(value, key) {
            jsonObject[key] = value;
        });
        
        const jsonToSend = {};
        jsonToSend["balance"] = jsonObject["balance"];
        jsonToSend["user"] = {"id":jsonObject["userId"]};
        
        // Make Fetch API call to submit form data
        fetch('/api/accounts/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonToSend)
        })
        .then(response => {
            // Parse the response body as JSON
            return response.json();
        })
        .then(data => {
            if (data.success) {
                // Redirect to a success page or handle success response
                alert('Created account successfully');
            } else {
                // Handle error response
                alert('Failed to create account');
                console.error('Failed to create account');
            }
            window.location.href = '/list-accts';
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
</script>

<%@ include file="common/footer.jspf"%>