/**
 * Created by garfi on 15/12/2015.
 */
window.addEventListener("load", function() { addJustLabels();});
function addJustLabels(){
    var elements = document.getElementsByClassName("legend");
    for(var i=1; i< elements.length ;i++ ){
        elements[i].setAttribute("class","legend label-"+i)
    }
    elements[0].setAttribute("class","legend label-"+elements.length);
}