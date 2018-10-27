
function addComment(address, contents, df, ddd, commendd) {
    localStorage.setItem("address", "Hello World!");
  const message = `Address: ${address}, Contents: ${contents}, ${df}, ${ddd}, ${commendd}`
  alert(message)
  return

}

function sendMessage(msg){
		window.android.finishPosting(msg);
		return
}


