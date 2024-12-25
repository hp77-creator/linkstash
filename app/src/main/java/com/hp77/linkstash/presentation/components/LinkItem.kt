package com.hp77.linkstash.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hp77.linkstash.domain.model.Link
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkItem(
    link: Link,
    onLinkClick: (Link) -> Unit,
    onEditClick: (Link) -> Unit,
    onToggleFavorite: (Link) -> Unit,
    onToggleArchive: (Link) -> Unit,
    onToggleStatus: (Link) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onLinkClick(link) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = link.title ?: link.url,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (link.title != null) {
                        Text(
                            text = link.url,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    Text(
                        text = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(link.createdAt)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

            }

            link.description?.let { description ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = link.type.getStatusLabel(link.isCompleted),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (link.isCompleted) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    link.completedAt?.let { timestamp ->
                        Text(
                            text = "Updated: ${SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(timestamp))}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                androidx.compose.material3.Switch(
                    checked = link.isCompleted,
                    onCheckedChange = { onToggleStatus(link) }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                if (link.tags.isNotEmpty()) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        TagChips(
                            tags = link.tags,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                Row {
                    IconButton(onClick = { onToggleFavorite(link) }) {
                        Icon(
                            imageVector = if (link.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (link.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (link.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { onEditClick(link) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit link"
                        )
                    }
                    IconButton(onClick = { onToggleArchive(link) }) {
                        Icon(
                            imageVector = if (link.isArchived) Icons.Default.Unarchive else Icons.Default.Archive,
                            contentDescription = if (link.isArchived) "Unarchive" else "Archive"
                        )
                    }
                    // Placeholder for download image button
                    IconButton(onClick = { /* TODO: Implement download */ }) {
                        Icon(
                            imageVector = Icons.Default.Share, // Temporary icon, replace with download icon later
                            contentDescription = "Share image"
                        )
                    }
                }
            }
        }
    }
}
