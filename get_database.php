<?php
//connect to DB
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$response = array();

//eng contacts
$result = mysql_query("SELECT * FROM EngineeringContacts") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["EngineeringContacts"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["ID"] = $row["ID"];
        $product["Name"] = $row["Name"];
        $product["Email"] = $row["Email"];
        $product["Position"] = $row["Position"];
        $product["Description"] = $row["Description"];
        array_push($response["EngineeringContacts"], $product);
    }
}

//emerg contacts
$result = mysql_query("SELECT * FROM EmergencyContacts") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["EmergencyContacts"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["ID"] = $row["ID"];
        $product["Name"] = $row["Name"];
        $product["PhoneNumber"] = $row["PhoneNumber"];;
        $product["Description"] = $row["Description"];
        array_push($response["EmergencyContacts"], $product);
    }
}

//buildings
$result = mysql_query("SELECT * FROM Buildings") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["Buildings"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["ID"] = $row["ID"];
        $product["Name"] = $row["Name"];
        $product["Purpose"] = $row["Purpose"];
        $product["BookRooms"] = $row["BookRooms"];
        $product["Food"] = $row["Food"];
        $product["ATM"] = $row["ATM"];
        $product["Lat"] = $row["Lat"];
        $product["Lon"] = $row["Lon"];
        array_push($response["Buildings"], $product);
    }
}

//food
$result = mysql_query("SELECT * FROM Food") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["Food"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["ID"] = $row["ID"];
        $product["Name"] = $row["Name"];
        $product["MealPlan"] = $row["MealPlan"];
        $product["Card"] = $row["Card"];
        $product["Information"] = $row["Information"];
        $product["BuildingID"] = $row["BuildingID"];
        $product["MonStartHours"] = $row["MonStartHours"];
        $product["MonStopHours"] = $row["MonStopHours"];
        $product["TueStartHours"] = $row["TueStartHours"];
        $product["TueStopHours"] = $row["TueStopHours"];
        $product["WedStartHours"] = $row["WedStartHours"];
        $product["WedStopHours"] = $row["WedStopHours"];
        $product["ThurStartHours"] = $row["ThurStartHours"];
        $product["ThurStopHours"] = $row["ThurStopHours"];
        $product["FriStartHours"] = $row["FriStartHours"];
        $product["FriStopHours"] = $row["FriStopHours"];
        $product["SatStartHours"] = $row["SatStartHours"];
        $product["SatStopHours"] = $row["SatStopHours"];
        $product["SunStartHours"] = $row["SunStartHours"];
        $product["SunStopHours"] = $row["SunStopHours"];
        array_push($response["Food"], $product);
    }
}

//cafs
$result = mysql_query("SELECT * FROM Cafeterias") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["Cafeterias"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["ID"] = $row["ID"];
        $product["Name"] = $row["Name"];
        $product["BuildingID"] = $row["BuildingID"];
        $product["WeekBreakfastStart"] = $row["WeekBreakfastStart"];
        $product["WeekBreakfastStop"] = $row["WeekBreakfastStop"];
        $product["FriBreakfastStart"] = $row["FriBreakfastStart"];
        $product["FriBreakfastStop"] = $row["FriBreakfastStop"];
        $product["SatBreakfastStart"] = $row["SatBreakfastStart"];
        $product["SatBreakfastStop"] = $row["SatBreakfastStop"];
        $product["SunBreakfastStart"] = $row["SunBreakfastStart"];
        $product["SunBreakfastStop"] = $row["SunBreakfastStop"];
        $product["WeekLunchStart"] = $row["WeekLunchStart"];
        $product["WeekLunchStop"] = $row["WeekLunchStop"];
        $product["FriLunchStart"] = $row["FriLunchStart"];
        $product["FriLunchStop"] = $row["FriLunchStop"];
        $product["SatLunchStart"] = $row["SatLunchStart"];
        $product["SatLunchStop"] = $row["SatLunchStop"];
        $product["SunLunchStart"] = $row["SunLunchStart"];
        $product["SunLunchStop"] = $row["SunLunchStop"];
        $product["WeekDinnerStart"] = $row["WeekDinnerStart"];
        $product["WeekDinnerStop"] = $row["WeekDinnerStop"];
        $product["FriDinnerStart"] = $row["FriDinnerStart"];
        $product["FriDinnerStop"] = $row["FriDinnerStop"];
        $product["SatDinnerStart"] = $row["SatDinnerStart"];
        $product["SatDinnerStop"] = $row["SatDinnerStop"];
        $product["SunDinnerStart"] = $row["SunDinnerStart"];
        $product["SunDinnerStop"] = $row["SunDinnerStop"];
        array_push($response["Cafeterias"], $product);
    }
}

if (count($response) > 0) {
    $response["Success"] = 1;
} else {
    $response["Success"] = 0;
}

echo json_encode($response);

?>
