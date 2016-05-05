/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getMyJSON() {
  var obj;
  $.getJSON(
    "http://maps.googleapis.com/maps/api/directions/json?origin=1301%20Ashwood%20Ct,%20San%20Mateo,%20CA&destination=200%20Oracle%20Pkwy,%20Redwood%20Shores,%20CA",
    function( data ) {
      obj = data;
  console.log(obj.toString());
  document.getElementById("demo").innerHTML = JSON.stringify(obj);
    }
  );
}
