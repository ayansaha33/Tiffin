<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="baseTemplate">
	<tiles:putAttribute name="body">



		<div style="overflow:scroll;width:100%;overflow:auto align="center">
			<h1>Incident List</h1>
			<h3>
				<a href="newIncident">New Incident</a>
			</h3>
			<table border="1">
				<th>No</th>
				<th>Incident_Id</th>
				<th>Priority</th>
				<th>Source</th>
				<th>Severity</th>
				<th>Open_Time</th>
				<th>Close_Time</th>
				<th>Closed_By</th>
				<th>L3_Assign_Time</th>
				<th>L2_Assign_Time</th>
				<th>Year</th>
				<th>Assignment_Group</th>
				<th>Description</th>
				<th>Language</th>
				<th>L2_Analysis</th>
				<th>Hpsm_Status</th>
				<th>L3_Status</th>
				<th>L2_Status</th>
				<th>Planned_Close_Date</th>
				<th>Affected_Service</th>
				<th>Age</th>

				<c:forEach var="incidents" items="${listIncidents}"
					varStatus="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${incidents.incident_id}</td>
						<td>${incidents.priority}</td>
						<td>${incidents.source}</td>
						<td>${incidents.severity}</td>
						<td>${incidents.open_time}</td>
						<td>${incidents.close_time}</td>
						<td>${incidents.closed_by}</td>
						<td>${incidents.l3_assign_time}</td>
						<td>${incidents.l2_assign_time}</td>
						<td>${incidents.year}</td>
						<td>${incidents.assignment_group}</td>
						<td>${incidents.description}</td>
						<td>${incidents.language}</td>
						<td>${incidents.l2_analysis}</td>
						<td>${incidents.hpsm_status}</td>
						<td>${incidents.l3_status}</td>
						<td>${incidents.l2_status}</td>
						<td>${incidents.planned_close_date}</td>
						<td>${incidents.affected_service}</td>
						<td>${incidents.age}</td>
						<td><a
							href="editIncident?incident_id=${incidents.incident_id}">Edit</a>
							&nbsp;&nbsp;&nbsp;&nbsp; <a
							href="deleteIncident?incident_id=${incidents.incident_id}">Delete</a>
						</td>

					</tr>
				</c:forEach>
			</table>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>