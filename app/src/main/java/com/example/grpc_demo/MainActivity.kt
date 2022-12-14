package com.example.grpc_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.grpc_demo.ui.theme.GRPC_DemoTheme
import io.grpc.ManagedChannelBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // this is just a demo how we can implement gRPC in android , this is not run on device because of we have not a sever
        val managedChannel = ManagedChannelBuilder.forAddress("https://www.google.com", 443).build()
        // ^ here we get Invalid host or port bcoz we have to use server ip or host
        val blockingStub = BookServiceGrpc.newBlockingStub(managedChannel)
        // if you are not found BookServiceGrpc or BookProto class than just build project once and it will create
        val bookRequest = BookProto.GetBookRequest.newBuilder().setIsbn(9123471293487).build()
        val book = blockingStub.getBook(bookRequest)

        setContent {
            GRPC_DemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(book.author)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GRPC_DemoTheme {
        Greeting("Android")
    }
}