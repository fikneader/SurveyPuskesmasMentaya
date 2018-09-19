<?php
	require_once('koneksi.php');

	$id_ibuhml	= $_POST['id_ibuhml'];

	$query = mysqli_query($conn, "SELECT COUNT(survey) FROM survey WHERE survey='PUAS'");
	$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    for($rows = array(); $row =  $result->fetch_assoc(); $rows[] = $row);
    print json_encode($rows);
} else {
    echo "0 results";
}
$conn->close();
?>