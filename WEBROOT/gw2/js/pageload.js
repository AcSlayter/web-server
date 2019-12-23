//function getTitleFromKey(){
//    var ajax_url = "/api/GUILDWARS/guildwars2/Items/getAchievementsDailyDetails";
//    $.ajax({
//        dataType: 'json',
//        url: ajax_url,
//        cache: false,
//        success: function(data){
//            console.log(data);
//            returnTheData(data)
//        }
//    });
//}
//
//function returnTheData(data){
//    var table =""
//    var results = "<table style=\"width:100%\">";
//    var header = "<tr><th>name</th> <th>description</th><th>requirement</th><th>count</th></tr>"
//    table = results + header;
//
//    for(var index=0, len =data.length ; index < len ; index++){
//        var entry = "<tr><td>" +  data[index].name +"</td><td>"+ data[index].description +"</td><td>"+data[index].requirement +"</td><td>"+  data[index].tiers[0].count + "</td></tr>";
//        table = table + entry;
////        console.log(data[index]);
////        console.log("name :" + data[index].name);
////        console.log("description :" + data[index].description);
////        console.log("requirement :" + data[index].requirement);
////        console.log("count :" + data[index].tiers[0].count);
//    }
//    table = table + "</table>"
//    console.log(table);
//    $("#test").innerHTML = table
//    document.getElementById("test").innerHTML = table;
//}
//
///*
//<table style="width:100%">
//  <tr>
//    <th>Firstname</th>
//    <th>Lastname</th>
//    <th>Age</th>
//  </tr>
//  <tr>
//    <td>Jill</td>
//    <td>Smith</td>
//    <td>50</td>
//  </tr>
//  <tr>
//    <td>Eve</td>
//    <td>Jackson</td>
//    <td>94</td>
//  </tr>
//  <tr>
//    <td>John</td>
//    <td>Doe</td>
//    <td>80</td>
//  </tr>
//</table>
//*/
//
//
//function getItemById(id){
//    var ajax_url = "/api/GUILDWARS/guildwars2/Items/getItems?ids=" + id ;
//    $.ajax({
//        dataType: 'json',
//        url: ajax_url,
//        cache: false,
//        success: function(data){
//            console.log(data);
//        }
//    });
//}
//
//
//$( document ).ready(function() {
     ////   console.log("test");
     //   getTitleFromKey();
     ////   getItemById("68149");
     //});
function getAccountCharacters(value){
   var ajax_url = "endpoint_Test/accountCharacters.json?account=".concat(value);
   $.ajax({
       dataType: 'json',
       url: ajax_url,
       cache: false,
       success: function(data){
           populatePlayerDropdown(data, "dropdown-account-characters")
       }
   });
}


document.getElementById("dropdown-account").addEventListener("change", function(event){
    var key = event.target.selectedOptions[0].label
    console.log(key);
    getAccountCharacters(key)
});

function getAccountName(){
    var ajax_url = "endpoint_Test/accounts.json";
    $.ajax({
        dataType: 'json',
        url: ajax_url,
        cache: false,
        success: function(data){
            populatePlayerDropdown(data,"dropdown-account")
        }
    });
}

function removeAllElements(dropDown){
    while(dropDown.length > 0){
        dropDown.remove(0);
    }
}

function populatePlayerDropdown(list,element){
    var dropDown = document.getElementById(element);
    removeAllElements(dropDown);
    var option = document.createElement("option");
    option.text = "...";
    dropDown.add(option);

    for(var index=0, len = list.length; index < len ; index++) {
        var option = document.createElement("option");
        option.text = list[index];
        dropDown.add(option);
    }
}

$( document ).ready(function() {
    getAccountName();

});
