# ActivityResult

easy way to startActivityForResult


- startActivityForResult with Coroutine:

```
lifecycleScope.launchWhenCreated{
                // create Intent
                var intent = Intent(this@MainActivity,NextActivity::class.java)
                intent.putExtra("key",edit.text.toString())
                
                // startActivity android wait result
                var result = this@MainActivity.startActivityWaitResult(intent,1001)

                // handle result
                if (result.isOk()) {
                   
                }
                if (result.isCanceled()){

                }
            }
```


- startActivityForResult with callback :

```
            // create Intent
            var intent = Intent(this@MainActivity,NextActivity::class.java)
            intent.putExtra("key",edit.text.toString())
            // startActivity and wait callback
            this@MainActivity.startActivityForResultBack(intent,1001){result->
                // handle result
                
                if (result.isOk()) {
                    
                }
                if (result.isCanceled()){
                   
                }
            }
```