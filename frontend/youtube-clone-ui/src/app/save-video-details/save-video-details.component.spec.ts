import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveVideoDetailsComponent } from './save-video-details.component';

describe('SaveVideoDetailsComponent', () => {
  let component: SaveVideoDetailsComponent;
  let fixture: ComponentFixture<SaveVideoDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaveVideoDetailsComponent]
    });
    fixture = TestBed.createComponent(SaveVideoDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
