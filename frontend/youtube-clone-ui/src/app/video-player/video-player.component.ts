import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent {
  //pass in the variable from the save-video-details.component by using @Input()
    @Input()
    videoUrl!: string | '';
    constructor(){}

    ngOnInit(): void {

    }
}
