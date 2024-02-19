package com.program.tech.youtubeclone.service;

import com.program.tech.youtubeclone.dto.CommentDto;
import com.program.tech.youtubeclone.dto.UploadVideoResponse;
import com.program.tech.youtubeclone.dto.VideoDto;
import com.program.tech.youtubeclone.model.Comment;
import com.program.tech.youtubeclone.model.Video;
import com.program.tech.youtubeclone.repository.UserRepository;
import com.program.tech.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;
    public UploadVideoResponse uploadVideo(MultipartFile multipartFile){
        //Upload file to AWS S3
        //Save Video Data to Database
        String videoUrl = s3Service.uploadFile(multipartFile);

        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        //Find the Video by VideoID
        var savedVideo = getVideoById(videoDto.getId());
        //Map the videoDto fields to Video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        //save the video to the database
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        //Find the video by videoId
        var savedVideo = getVideoById(videoId);

        String thumbnailUrl = s3Service.uploadFile(file);

        savedVideo.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(savedVideo);
        return thumbnailUrl;
    }

    Video getVideoById(String videoId){
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Video by id -" + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
       Video savedVideo = getVideoById(videoId);

       increaseVideoCount(savedVideo);
       userService.addVideoToHistory(videoId);

        VideoDto videoDto = mapToVideoDto(savedVideo);

        return videoDto;
    }

    public VideoDto likeVideo(String videoId) {
        //Get Video by id
        Video videoById = getVideoById(videoId);

        //increment like Count
        //If user already liked the video, then decrement like count
        // If user already dislike the video, then increment like count and decrement dislike count

        if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDislikedVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDislikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        } else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(videoById);

        VideoDto videoDto = mapToVideoDto(videoById);
        return videoDto;
    }

    private void increaseVideoCount(Video savedVideo){
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto dislikeVideo(String videoId) {
        //Get Video by id
        Video videoById = getVideoById(videoId);
        //opposite of like = dislike video

        if(userService.ifDislikedVideo(videoId)){
            videoById.decrementDisLikes();
            userService.removeFromDislikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        } else {
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }
        videoRepository.save(videoById);


        VideoDto videoDto = mapToVideoDto(videoById);

        return videoDto;
    }

    private static VideoDto mapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDisLikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return videoDto;
    }

    public void addComment(String videoId, CommentDto commentDto) {
        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentDto.getCommentText());
        comment.setAuthorId(commentDto.getAuthorId());

        video.addComment(comment);
        videoRepository.save(video);
    }

    public List<CommentDto> getAllComments(String videoId) {
        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getCommentList();

        return commentList.stream().map(this::mapToCommentDto).toList();
       // return commentList.stream().map(comment -> mapToCommentDto(comment)).toList();
    }

    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getText());
        commentDto.setAuthorId(comment.getAuthorId());
        return commentDto;
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream().map(video -> mapToVideoDto(video)).toList();
    }
}
