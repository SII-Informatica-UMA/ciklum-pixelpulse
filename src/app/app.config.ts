import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideAnimationsAsync()]
};
export const BACKEND_URI = 'http://localhost:8080';
export const SECRET_JWT='secret';
export const FRONTEND_URI = 'http://localhost:4200';