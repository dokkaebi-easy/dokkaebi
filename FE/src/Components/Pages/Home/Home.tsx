import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

export default function Home() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={3}>
        <Grid item xs />
        <Grid item xs={8} />
      </Grid>
    </Box>
  );
}
