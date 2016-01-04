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
        var primaryResultName = $('#uitkomst > .primaryPType').text();
        var secondaryResultName = $('#uitkomst > .secondaryPType').text();
        var primaryResultInfoText = $('#primaryType-info > p').text();
        var secondaryResultInfoText = $('#secondaryType-info > p').text();

        /******** cuts the string into lines so it fits on the page ************/
        var primaryResultInfoTextP = doc.splitTextToSize(primaryResultInfoText,200);
        var secondaryResultInfoTextP = doc.splitTextToSize(secondaryResultInfoText,200);

        /***** remove excess whitespace from beginning and end of string ***/
        primaryResultName = primaryResultName.trim();
        secondaryResultName = secondaryResultName.trim();

        /*** constants **/
        var headerSize = 18;
        var textSize = 15;
        var textOffset = 10;
        var titleSize = 20;


        doc.setTextColor(0,0,0);
        doc.setFontSize(titleSize);
        doc.setFontType("bold");
        doc.setFont("courier");
        doc.text(textOffset,30, 'Resultaten');
        doc.setFontType("normal");
        doc.setFontSize(headerSize);
        doc.text(textOffset,40,primaryResultName);
        doc.text(textOffset,50,secondaryResultName);

        var barChart = document.getElementById('barChartContainer').firstChild.firstChild;

        doc.addImage(uri, 'PNG', 55, 50, 100, 100);
        doc.addImage(barChart.toDataURL(), 'PNG', 45, 155, 100, 100);

        /**** primary result info page ********/
        doc.addPage();
        doc.setFontSize(headerSize);
        doc.setFontType("bold");
        doc.text(textOffset,30, primaryResultName.split(" ").splice(-1)[0]);

        doc.setFontType("normal");
        doc.setFontSize(textSize);
        doc.text(textOffset,40, primaryResultInfoTextP);

        /**** secondary result info page ********/

        doc.addPage();
        doc.setFontSize(headerSize);
        doc.setFontType("bold");
        doc.text(textOffset,30, secondaryResultName.split(" ").splice(-1)[0]);

        doc.setFontType("normal");
        doc.setFontSize(textSize);
        doc.text(textOffset,40, secondaryResultInfoTextP);

        doc.save('persoonlijkheidsTypeTest.pdf');
    });
}