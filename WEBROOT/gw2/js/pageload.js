
function generateCharacterBlocks(data){
    var element  = document.getElementById('character-info');
    var fragment = document.createDocumentFragment();

    var section = document.createElement('section');
    section.className = "character";

    var divName = document.createElement('div');
    divName.className = "name";
    divName.textContent = data.name;

    var divProfession = document.createElement('div');
    divProfession.className = "profession";
    divProfession.textContent = data.profession;

    var divrgl = document.createElement('div');
    divrgl.className = "rgl";
    divrgl.textContent = data.race + " - " + data.gender + " - " + data.level + " (level)";

    var divDad = document.createElement('div');
    divDad.className = "dad";
    divDad.textContent = new Intl.DateTimeFormat('en-US').format(new Date(data.created)) + " - " + s_to_days(data.age) + "(days) - " + data.deaths + " (deaths)";

    section.appendChild(divName);
    section.appendChild(divProfession);
    section.appendChild(divrgl);
    section.appendChild(divDad);


    fragment.appendChild(section);
    element.appendChild(fragment);
}

function s_to_days(seconds){
    min = seconds / 60;
    hour = min /60;
    day = hour/24;
    return day.toFixed(1);
}

function getAccountCharacterCore(account, value){
   var ajax_url = "api/core?account=" + account + "&name=" + value
   // var ajax_url = "endpoint_Test/core.json?account=".concat(account).concat("&name=").concat(value);
   $.ajax({
       dataType: 'json',
       url: ajax_url,
       cache: false,
       success: function(data){
         generateCharacterBlocks(data);
       }
   });
}

function getAccountCharacters(value){
   //var ajax_url = "endpoint_Test/accountCharacters.json?account=".concat(value);
   var ajax_url = "api/accountCharacters?account=".concat(value);
   $.ajax({
       dataType: 'json',
       url: ajax_url,
       cache: false,
       success: function(data){
           for(var index=0, len = data.characterName.length ; index < len; index++){
               getAccountCharacterCore(value, data.characterName[index]);
           }
       }
   });
}


document.getElementById("dropdown-account").addEventListener("change", function(event){
    var element  = document.getElementById('character-info');
    element.innerHTML = "";
    var key = event.target.selectedOptions[0].label
    getAccountCharacters(key)
});

function getAccountName(){
  //  var ajax_url = "endpoint_Test/accounts.json";
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
