$('document').ready(function (){
    $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (subscription, status){
            $('#idEdit').val(subscription.id);
            $('#nameEdit').val(subscription.name);
            $('#priceEdit').val(subscription.cost);
        });
        $('#editModal').modal();
    });
});
