/**
 * Created by garfi on 16/12/2015.
 */
// Using jsPDF to save the page as PDF.
var over = '<div id="overlay">' +
    '<img id="loading" src="../imgs/loading.gif">' +
    '</div>';
$(document).ready(function(){
    $(over).appendTo('body');
    console.log("added overlay")
});
$(window).load(function(){
    $('#overlay').remove();
    console.log("remove overlay");
});

function enablePDFButton(){
    console.log("pdfButtonEnabled")
    document.getElementsByClassName("downloadPDF")[0].removeAttribute("disabled");
}

function PDFFromHTML() {

    svgAsPngUri(document.getElementById("radarChartBox").firstChild, {}, function(uri) {

        if(document.getElementById('barChartContainer').firstChild == null)
            return alert('Wacht tot dat de pagina volledig geladen is.');

        var doc = new jsPDF();
        doc.setTextColor(0,0,0);
        doc.text(15, 20, 'Resultaten');
        var specialElementHandlers = {
            '#radarChartBox': function(element, renderer){
                return true;
            },
            '#barChartContainer': function(element, renderer){
                return true;
            }
        };
        doc.setTextColor(0,0,0);
        doc.fromHTML($('#uitkomst').get(0), 15, 15, {
            'width': 170,
            'elementHandlers': specialElementHandlers
        });

        var barChart = document.getElementById('barChartContainer').firstChild.firstChild;

        doc.addImage(uri, 'PNG', 55, 40, 100, 100);
        doc.addImage(barChart.toDataURL(), 'PNG', 40, 155, 100, 100);


        doc.addPage();
        doc.fromHTML($('#primaryType-info').get(0), 15, 15, {
            'width': 170,
            'elementHandlers': specialElementHandlers
        });

        doc.addPage();
        doc.fromHTML($('#secondaryType-info').get(0), 15, 15, {
            'width': 170,
            'elementHandlers': specialElementHandlers
        });
        doc.save('persoonlijkheidsTypeTest.pdf');
    });
}