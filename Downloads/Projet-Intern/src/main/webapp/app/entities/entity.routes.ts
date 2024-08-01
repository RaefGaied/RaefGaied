import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'projetinternApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'hotel-administrateur',
    data: { pageTitle: 'projetinternApp.hotelAdministrateur.home.title' },
    loadChildren: () => import('./hotel-administrateur/hotel-administrateur.routes'),
  },
  {
    path: 'hotel',
    data: { pageTitle: 'projetinternApp.hotel.home.title' },
    loadChildren: () => import('./hotel/hotel.routes'),
  },
  {
    path: 'ui-configuration',
    data: { pageTitle: 'projetinternApp.uIConfiguration.home.title' },
    loadChildren: () => import('./ui-configuration/ui-configuration.routes'),
  },
  {
    path: 'email-template-configuration',
    data: { pageTitle: 'projetinternApp.emailTemplateConfiguration.home.title' },
    loadChildren: () => import('./email-template-configuration/email-template-configuration.routes'),
  },
  {
    path: 'authentification-configuration',
    data: { pageTitle: 'projetinternApp.authentificationConfiguration.home.title' },
    loadChildren: () => import('./authentification-configuration/authentification-configuration.routes'),
  },
  {
    path: 'service',
    data: { pageTitle: 'projetinternApp.service.home.title' },
    loadChildren: () => import('./service/service.routes'),
  },
  {
    path: 'partenaire',
    data: { pageTitle: 'projetinternApp.partenaire.home.title' },
    loadChildren: () => import('./partenaire/partenaire.routes'),
  },
  {
    path: 'reservation',
    data: { pageTitle: 'projetinternApp.reservation.home.title' },
    loadChildren: () => import('./reservation/reservation.routes'),
  },
  {
    path: 'paiement',
    data: { pageTitle: 'projetinternApp.paiement.home.title' },
    loadChildren: () => import('./paiement/paiement.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
