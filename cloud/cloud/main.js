Parse.Cloud.define("startSession", function(request, response) {
	
  var recipientId = request.params.recipientId;
  
  var pushQuery = new Parse.Query(Parse.Installation);
  pushQuery.equalTo("user", recipientId);


  Parse.Push.send({
    where: pushQuery,
    data: {
      alert: request.params.userId
    }
  }).then(function() {
      response.success("Push was sent successfully.")
  }, function(error) {
      response.error("Push failed to send with error: " + error.message);
  });
});
