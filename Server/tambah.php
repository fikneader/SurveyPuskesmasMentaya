<?php
	
	require_once('koneksi.php');

	$no       = $_POST['no'];
	$survey = $_POST['survey'];
	$tanggal      = $_POST['tanggal'];
	$jam	      = $_POST['jam'];   

	$query 		= mysqli_query($conn, "INSERT INTO survey (no, survey, tanggal, jam)VALUES ('$no','$survey', '$tanggal', '$jam') ");

	if($query) {
	    print json_encode(array( 'status'=>'success', 'message'=>'Berhasil memasukan data' ));
	} else {\
	    print json_encode(array( 'status'=>'failed','message'=>'gagal memasukkan data' ));
	}
?>