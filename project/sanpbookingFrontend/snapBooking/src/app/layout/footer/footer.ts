import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-footer',
  imports: [ReactiveFormsModule],
  templateUrl: './footer.html',
  styleUrl: './footer.css'
})
export class Footer {

  newsletterForm: FormGroup;
constructor(private fb: FormBuilder) {
    this.newsletterForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  submitEmail() {
    if (this.newsletterForm.valid) {
      const email = this.newsletterForm.value.email;
    }
  }

  onReadMoreClick(): void {
  }

  onServiceClick(service: string): void {
  }

  onTweetClick(tweetId: string): void {
  }

  openSocialLink(platform: string): void {
    switch (platform) {
      case 'facebook':
        window.open('https://facebook.com', '_blank');
        break;
      case 'google':
        window.open('https://google.com', '_blank');
        break;
      case 'twitter':
        window.open('https://twitter.com', '_blank');
        break;
    }
  }

}
