package com.cengiztoru.constraintlayoutinjetpackcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlin.random.Random

@Composable
fun ConstraintLayoutScreen() {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (
            gradientBackground,
            profileImage, notification,
            greetingMessage, askingMessage, urgentCare, doctorImage,
            bottomSheetCard,
            ourServicesText,
            consultationIcon, medicinesIcon, ambulanceIcon,
            consultationText, medicinesText, ambulanceText,
        ) = createRefs()

        //region Guideline Example
        val horizontalCenterGuideline = createGuidelineFromTop(0.45f)
        Image(
            painterResource(id = R.drawable.gradient_background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .alpha(0.8f)
                .constrainAs(gradientBackground) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(horizontalCenterGuideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )
        //endregion

        //region Chain Example
        val topGuideline = createGuidelineFromTop(16.dp)
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)

        createHorizontalChain(profileImage, notification, chainStyle = ChainStyle.SpreadInside)
        Image(painterResource(id = R.drawable.profile),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(profileImage) {
                    top.linkTo(topGuideline)            //or just "top.linkTo(parent.top, margin = 16.dp)"
                }
                .padding(start = 16.dp)
                .clip(CircleShape)
                .size(42.dp))

        Image(painterResource(id = R.drawable.notification),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(notification) {
                    top.linkTo(profileImage.top)
                    bottom.linkTo(profileImage.bottom)
                }
                .padding(end = 16.dp)
                .clip(CircleShape)
                .size(42.dp))

        //endregion

        //region Barrier Example
        val greetingBarrier = createEndBarrier(
            greetingMessage, askingMessage,
        )
        val userName = if (Random.nextInt(0, 10) % 2 == 0) "Cengiz" else "Cengiz TORU"
        val greetingMessageWithUser = "Welcome! \n$userName"
        Text(text = greetingMessageWithUser,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(greetingMessage) {
                top.linkTo(profileImage.bottom, margin = 32.dp)
                start.linkTo(startGuideline)
            }
        )

        Text(text = "How is it going today?",
            color = Color.Gray,
            modifier = Modifier.constrainAs(askingMessage) {
                top.linkTo(greetingMessage.bottom, 16.dp)
                start.linkTo(greetingMessage.start)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.urgent_care),
            contentDescription = "",
            modifier = Modifier.constrainAs(urgentCare) {
                top.linkTo(askingMessage.bottom, margin = 32.dp)
                start.linkTo(askingMessage.start)
                end.linkTo(askingMessage.end)
            })

        Image(
            painterResource(id = R.drawable.doctor),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .constrainAs(doctorImage) {
                    top.linkTo(notification.bottom, margin = 8.dp)
                    start.linkTo(greetingBarrier)
                    end.linkTo(notification.end)
                    bottom.linkTo(horizontalCenterGuideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )
        //endregion

        //region Bottom Sheet
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier.constrainAs(bottomSheetCard) {
                top.linkTo(horizontalCenterGuideline, margin = (-4).dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {}

        Text(text = "Our services",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(ourServicesText) {
                top.linkTo(bottomSheetCard.top, margin = 24.dp)
                start.linkTo(bottomSheetCard.start, margin = 16.dp)
            }
        )

        //region Chain
        createHorizontalChain(
            consultationIcon,
            medicinesIcon,
            ambulanceIcon,
            chainStyle = ChainStyle.Spread
        )
        Image(painterResource(id = R.drawable.consultation_icon),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(consultationIcon) {
                    top.linkTo(ourServicesText.bottom, margin = 16.dp)
                }
        )
        Image(painterResource(id = R.drawable.medicines_icon),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(medicinesIcon) {
                    top.linkTo(consultationIcon.top)
                    bottom.linkTo(consultationIcon.bottom)
                }
        )
        Image(painterResource(id = R.drawable.ambulance_icon),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(ambulanceIcon) {
                    top.linkTo(consultationIcon.top)
                    bottom.linkTo(consultationIcon.bottom)
                }
        )
        //endregion Chain

        Text(text = "Consultation",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(consultationText) {
                top.linkTo(consultationIcon.bottom, 12.dp)
                start.linkTo(consultationIcon.start)
                end.linkTo(consultationIcon.end)
            }
        )

        Text(text = "Medicines",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(medicinesText) {
                top.linkTo(consultationText.top)
                start.linkTo(medicinesIcon.start)
                end.linkTo(medicinesIcon.end)
            }
        )
        Text(text = "Ambulance",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(ambulanceText) {
                top.linkTo(consultationText.top)
                start.linkTo(ambulanceIcon.start)
                end.linkTo(ambulanceIcon.end)
            }
        )

        //Appointment Area
        val (
            appointmentText, seeAllText,
            appointmentItemCard,
            appointmentDateText,
            appointmentDateIcon, appointmentDate, appointmentOptions,
            appointmentDivider,
            appointmentDoctorProfileImage, appointmentDoctorName,
            appointmentDoctorBranch
        ) = createRefs()

        //region Two text without chain
        //we can do these text same as profile and notification icons with chain
        Text(text = "Appointment",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(appointmentText) {
                top.linkTo(consultationText.bottom, margin = 30.dp)
                start.linkTo(startGuideline)
            }
        )
        Text(text = "See All",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.constrainAs(seeAllText) {
                top.linkTo(appointmentText.top)
                end.linkTo(endGuideline)
            }
        )
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.constrainAs(appointmentItemCard) {
                top.linkTo(appointmentText.bottom, margin = 16.dp)
                start.linkTo(appointmentText.start)
                end.linkTo(endGuideline)
                bottom.linkTo(appointmentDoctorProfileImage.bottom, margin = (-16).dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .wrapContentSize(Alignment.TopStart)
                    .fillMaxHeight()
                    .background(Color(0xAA4D94FF))

            ) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Text(text = "Appointment date",
            color = Color.Gray,
            modifier = Modifier.constrainAs(appointmentDateText) {
                top.linkTo(appointmentItemCard.top, 16.dp)
                start.linkTo(appointmentItemCard.start, 24.dp)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.time),
            contentDescription = "",
            modifier = Modifier.constrainAs(appointmentDateIcon) {
                top.linkTo(appointmentDateText.bottom, margin = 8.dp)
                start.linkTo(appointmentDateText.start)
            })

        Text(text = "Mon Nov 7 | 8:00 - 8:30 AM",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(appointmentDate) {
                top.linkTo(appointmentDateIcon.top)
                start.linkTo(appointmentDateIcon.end, 4.dp)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.options),
            contentDescription = "",
            modifier = Modifier.constrainAs(appointmentOptions) {
                end.linkTo(appointmentItemCard.end, margin = 8.dp)
                top.linkTo(appointmentDateText.top)
                bottom.linkTo(appointmentDate.bottom)
            })

        Divider(
            color = Color.Gray,
            thickness = (0.5f).dp,
            modifier = Modifier
                .constrainAs(appointmentDivider) {
                    top.linkTo(appointmentDate.bottom, margin = 16.dp)
                    start.linkTo(appointmentDateIcon.start)
                    end.linkTo(appointmentOptions.end)
                    width = Dimension.fillToConstraints
                }
                .alpha(0.5f)
        )

        Image(
            painter = painterResource(id = R.drawable.appointment_doctor),
            contentDescription = "",
            modifier = Modifier.constrainAs(appointmentDoctorProfileImage) {
                top.linkTo(appointmentDivider.bottom, margin = 16.dp)
                start.linkTo(appointmentDivider.start)
            })

        Text(text = "Dr. Rıdvan TORU",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(appointmentDoctorName) {
                top.linkTo(appointmentDoctorProfileImage.top)
                start.linkTo(appointmentDoctorProfileImage.end, 12.dp)
            }
        )
        Text(text = "Dentist",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.constrainAs(appointmentDoctorBranch) {
                top.linkTo(appointmentDoctorName.bottom, margin = 2.dp)
                start.linkTo(appointmentDoctorName.start)
            }
        )

        //endregion Bottom Sheet
    }
}

@Preview
@Composable
fun ConstraintLayoutScreenPreview() {
    ConstraintLayoutScreen()
}