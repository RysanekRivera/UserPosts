package com.rysanek.userposts.ui.presentation.screens.userposts

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rysanek.userposts.R
import com.rysanek.userposts.data.models.comments.Comment
import com.rysanek.userposts.data.models.posts.Post
import com.rysanek.userposts.data.models.users.User
import com.rysanek.userposts.ui.viewmodels.FetchViewModel

@Composable
fun UserItems(
    users: List<User>,
) {

    LazyColumn(
        state = rememberLazyListState()
    ) {
        items(users) { user -> UserItem(user = user) }
    }
}

@Composable
private fun UserItem(
    user: User,
    fetchViewModel: FetchViewModel = hiltViewModel()
) {

    var shouldFetchPosts by rememberSaveable { mutableStateOf(user.posts.isNullOrEmpty()) }
    var posts by rememberSaveable { mutableStateOf(listOf<Post>()) }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

    ) {
        val rowModifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()

        Row(modifier = rowModifier) {
            Text(text = "Id:", modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), color = MaterialTheme.colorScheme.surface, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = user.id.toString(), modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), color = MaterialTheme.colorScheme.surface, fontWeight = FontWeight.ExtraBold)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = rowModifier) {
            Text(text = "Name:", modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), color = MaterialTheme.colorScheme.surface, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = user.name, modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), color = MaterialTheme.colorScheme.surface, fontWeight = FontWeight.ExtraBold)
        }

        PostItems(posts = posts)

    }

    if (shouldFetchPosts) {
        fetchViewModel.fetchPosts(
            user = user,
            onLoading = {},
            onError = {},
            onPostReceived = {
                user.posts = it
                posts = it
            },
            onCompletion = {
                shouldFetchPosts = false
            }

        )
    } else {
        posts = user.posts ?: emptyList()
    }

}

@Composable
fun PostItems(
    posts: List<Post>
) {
    var expandedPosts by rememberSaveable { mutableStateOf(false) }
    val numberOfPosts by animateIntAsState(targetValue = posts.size, animationSpec = tween(400, 200, FastOutLinearInEasing))

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedPosts = !expandedPosts },
            verticalAlignment = CenterVertically,

            ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = CenterVertically,

                ) {
                Text(text = "Posts: ", modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), fontWeight = FontWeight.ExtraBold)

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = numberOfPosts.toString())

            }

            IconButton(
                onClick = { expandedPosts = !expandedPosts },
            ) {
                Icon(
                    imageVector = if (expandedPosts) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expandedPosts) stringResource(R.string.show_less) else stringResource(
                        R.string.show_more
                    )
                )
            }

        }

        if (expandedPosts) {
            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(if (expandedPosts) 450.dp else 0.dp)
            ) {

                items(posts) { post ->

                    PostItem(post = post)

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }

    }


}

@Composable
fun PostItem(
    post: Post,
    fetchViewModel: FetchViewModel = hiltViewModel()
) {
    var shouldFetchComments by rememberSaveable { mutableStateOf(post.comments.isNullOrEmpty()) }
    var comments by rememberSaveable { mutableStateOf(listOf<Comment>()) }

    Card(
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = post.title.toString(),
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = post.body.toString())
        }

        CommentItems(comments = comments)
    }

    if (shouldFetchComments) {
        fetchViewModel.fetchComments(
            post = post,
            onLoading = {},
            onError = {},
            onCommentsReceived = {
                post.comments = it
                comments = it

            },
            onCompletion = {
                shouldFetchComments = false
            }

        )
    } else {
        comments = post.comments ?: emptyList()
    }

}

@Composable
fun CommentItems(
    comments: List<Comment>,
) {
    var expandedComments by rememberSaveable { mutableStateOf(false) }
    val numberOfComments by animateIntAsState(targetValue = comments.size, animationSpec = tween(400, 200, FastOutLinearInEasing))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedComments = !expandedComments },
            verticalAlignment = CenterVertically,

            ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = CenterVertically,

                ) {
                Text(text = "Comments: ", modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), fontWeight = FontWeight.ExtraBold)

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = numberOfComments.toString(), modifier = Modifier.padding(start = 8.dp), fontSize = TextUnit(18f, TextUnitType.Sp), fontWeight = FontWeight.ExtraBold)

            }

            IconButton(
                onClick = { expandedComments = !expandedComments },
            ) {
                Icon(
                    imageVector = if (expandedComments) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expandedComments) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                )
            }

        }

        if (expandedComments) {
            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(if (expandedComments) 450.dp else 0.dp)
            ) {

                items(comments) { comment ->

                    CommentItem(comment = comment)

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }

    }

}

@Composable
fun CommentItem(
    comment: Comment
) {

    Card(
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.tertiary)
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = comment.name.toString(),
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = comment.body.toString())
        }

    }

}