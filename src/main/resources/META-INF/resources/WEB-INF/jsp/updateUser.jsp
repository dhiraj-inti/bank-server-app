
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Update User</h1>
	<form:form method="post" action="update-user" modelAttribute="user">
		<fieldset class="mb-3">
			<form:label path="fullName">Full Name</form:label>
			<form:input type="text" name="fullName"
			 path="fullName" required="required" />
			<form:errors path="fullName" cssClass="text-warning" />
		</fieldset>
		<fieldset class="mb-3">
			<form:label path="contact">Contact</form:label>
			<form:input type="text" name="contact" path="contact"
				required="required" />
			<form:errors path="contact" cssClass="text-warning" />
		</fieldset>

		<button type="submit" class="btn btn-primary">Submit</button>

		<form:input type="hidden" name="id" path="id" />
		<form:input type="hidden" name="username" path="username" />
		<form:input type="hidden" name="password" path="password" />
	</form:form>

</div>

<%@ include file="common/footer.jspf"%>
