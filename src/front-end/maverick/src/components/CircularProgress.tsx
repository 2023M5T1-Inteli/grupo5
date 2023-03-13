//Import components
import CircularProgress from '@mui/material/CircularProgress';

//Import libraries
import Box from '@mui/material/Box';

// Render the components to export for HTML
 function CircularIndeterminate() {
  return (
    <Box sx={{ display: 'flex' }}>
      <CircularProgress />
    </Box>
  );
}
export default CircularIndeterminate;