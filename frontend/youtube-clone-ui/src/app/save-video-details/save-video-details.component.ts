import { Component, inject } from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { FormControl, FormGroup } from '@angular/forms';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatIconModule } from '@angular/material/icon';
import {MatChipInputEvent} from '@angular/material/chips';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { VideoDto } from '../video-dto';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { HttpHeaders } from '@angular/common/http';
import { ResourceLoader } from '@angular/compiler';
import { Router } from '@angular/router';
@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css'],

})
export class SaveVideoDetailsComponent {

  
  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  videoStatus: FormControl = new FormControl('');

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];
  selectedFile!: File;
  selectedFileName = '';
  videoId = '';
  fileSelected = false;
  videoUrl!: string;
  thumbnailUrl!: string;

  announcer = inject(LiveAnnouncer);

  constructor(private activatedRoute: ActivatedRoute, 
              private videoService: VideoService, 
              private Matsnackbar: MatSnackBar,
              private router : Router){
    
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    console.log('this',this.videoId);
    this.videoService.getVideo(this.videoId).subscribe(data =>{
      this.videoUrl = data.videoUrl;
      console.log("Video URL:",data.videoUrl);
    })
    this.saveVideoDetailsForm = new FormGroup({
      //Define Form Controlls
      title: this.title,
      description:this.description,
      videoStatus:this.videoStatus,

    })
  }

  ngOnInit(): void{
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(value: string): void {
    const index = this.tags.indexOf(value);

    if (index >= 0) {
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed ${value}`);
    }
  }

  onFileSelected(event : Event){
    //@ts-ignore
      this.selectedFile = event.target.files[0];
      this.selectedFileName = this.selectedFile.name;
      this.fileSelected = true
  }

  onUpload(){
    this.videoService.uploadThumbnail(this.selectedFile, this.videoId)
      .subscribe(data => {
        console.log("thumbnailURL",data);
        //show an upload success notification.
        this.thumbnailUrl = data;
        console.log("thumbnailURL",this.thumbnailUrl);
        this.Matsnackbar.open("Thumbnail Uploaded Successfully","OK");
      })
  }

  saveVideo(){
    //Call the video service to make a http call to our backend
    console.log("inside saveVideo:")
    const videoMetaData: VideoDto = {
      "id": this.videoId,
      "title": this.saveVideoDetailsForm.get('title')?.value ,
      "description": this.saveVideoDetailsForm.get('description')?.value,
      "tags": this.tags,
      "videoUrl": this.videoUrl,
      "videoStatus": this.saveVideoDetailsForm.get('videoStatus')?.value,
      "thumbnailUrl": this.thumbnailUrl,
      "likeCount": 0,
      "dislikeCount": 0,
      "viewCount": 0

      
      
    }
    console.log(videoMetaData);
    this.videoService.saveVideo(videoMetaData).subscribe(data=>{
      this.Matsnackbar.open("Video metaData Updated successfully","OK");
      this.router.navigateByUrl("/featured");
    });
  }


  // edit(fruit: Fruit, event: MatChipEditedEvent) {
  //   const value = event.value.trim();

  //   // Remove fruit if it no longer has a name
  //   if (!value) {
  //     this.remove(fruit);
  //     return;
  //   }

  //   // Edit existing fruit
  //   const index = this.tags.indexOf(fruit);
  //   if (index >= 0) {
  //     this.tags[index].name = value;
  //   }
  // }

}
