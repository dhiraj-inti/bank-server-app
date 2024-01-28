
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Update User</h1>
	<form:form method="post" action="update-acct" modelAttribute="account">
		<fieldset class="mb-3">
			<form:label path="balance">Balance</form:label>
			<form:input type="number" name="balance"
			 path="balance" required="required" />
			<form:errors path="balance" cssClass="text-warning" />
		</fieldset>

		<button type="submit" class="btn btn-primary">Submit</button>

		<form:input type="hidden" name="id" path="id" />
	</form:form>

</div>

<%@ include file="common/footer.jspf"%>
