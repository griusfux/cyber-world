var argv = function () {
    // This function is anonymous, is executed immediately and
    // the return value is assigned to QueryString!
    var query_string = {};
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        // If first entry with this name
        if (typeof query_string[pair[0]] === "undefined") {
            query_string[pair[0]] = pair[1];
            // If second entry with this name
        } else if (typeof query_string[pair[0]] === "string") {
            var arr = [ query_string[pair[0]], pair[1] ];
            query_string[pair[0]] = arr;
            // If third or later entry with this name
        } else {
            query_string[pair[0]].push(pair[1]);
        }
    }
    return query_string;
} ();

function angleBetweenQuats(qBefore,qAfter) {
    q1 = new THREE.Quaternion();
    q1.copy(qBefore);
    q1.inverse();
    q1.multiply(qAfter);
    var halfTheta = Math.acos( q1.w );
    return 2*halfTheta;
}

function lookTowards(fromObject,toPosition, dTheta) {
    var quat0 = new THREE.Quaternion();
    quat0.setFromRotationMatrix( fromObject.matrix );
    var eye = fromObject.position;
    var center = toPosition;
    var up = new THREE.Vector3(0,1,0);
    var mat = new THREE.Matrix4();
    mat.lookAt(center,eye,up);
    var quat1 = new THREE.Quaternion();
    quat1.setFromRotationMatrix( mat );
    var deltaTheta = angleBetweenQuats(quat0,quat1);
    var frac = dTheta/deltaTheta;
    if (frac>1)  frac=1;
    fromObject.quaternion.slerp(quat1,frac);
}
