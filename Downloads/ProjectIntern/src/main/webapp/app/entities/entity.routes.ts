import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'projectinternApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'hotel-administrateur',
    data: { pageTitle: 'projectinternApp.hotelAdministrateur.home.title' },
    loadChildren: () => import('./hotel-administrateur/hotel-administrateur.routes'),
  },
  {
    path: 'hotel',
    data: { pageTitle: 'projectinternApp.hotel.home.title' },
    loadChildren: () => import('./hotel/hotel.routes'),
  },
  {
    path: 'ui-configuration',
    data: { pageTitle: 'projectinternApp.uIConfiguration.home.title' },
    loadChildren: () => import('./ui-configuration/ui-configuration.routes'),
  },
  {
    path: 'email-template-configuration',
    data: { pageTitle: 'projectinternApp.emailTemplateConfiguration.home.title' },
    loadChildren: () => import('./email-template-configuration/email-template-configuration.routes'),
  },
  {
    path: 'authentification-configuration',
    data: { pageTitle: 'projectinternApp.authentificationConfiguration.home.title' },
    loadChildren: () => import('./authentification-configuration/authentification-configuration.routes'),
  },
  {
    path: 'service',
    data: { pageTitle: 'projectinternApp.service.home.title' },
    loadChildren: () => import('./service/service.routes'),
  },
  {
    path: 'partenaire',
    data: { pageTitle: 'projectinternApp.partenaire.home.title' },
    loadChildren: () => import('./partenaire/partenaire.routes'),
  },
  {
    path: 'reservation',
    data: { pageTitle: 'projectinternApp.reservation.home.title' },
    loadChildren: () => import('./reservation/reservation.routes'),
  },
  {
    path: 'paiement',
    data: { pageTitle: 'projectinternApp.paiement.home.title' },
    loadChildren: () => import('./paiement/paiement.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
