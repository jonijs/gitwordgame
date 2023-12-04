    package com.example.composewordgame1.screens

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.*
    //import androidx.compose.foundation.layout.BoxScopeInstance.align
    //import androidx.compose.foundation.layout.BoxScopeInstance.align
    import androidx.compose.foundation.selection.selectable
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.RadioButton
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.MutableState
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryRow(selected:MutableState<String>,image : Int,txt: String) {


        Row(
            modifier = Modifier
                //.align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth(1f)
                .selectable(true, onClick = {})
        ) {

            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 1.dp)
                    .fillMaxWidth(1f)
                    .border(
                        width = 1.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(percent = 10)
                    )
            )
            {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.size(size = 40.dp)
                            .clip(shape = CircleShape),
                        painter = painterResource(id = image),
                        contentDescription = txt
                    )
                    var bnvalue = txt
                    RadioButton(
                        onClick = {
                            selected.value = bnvalue

                        },
                        selected = (txt == selected.value)
                    )

                }

            }
        }
    }



